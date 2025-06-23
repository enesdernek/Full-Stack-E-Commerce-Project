package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.OrderDto;
import io.github.enesdernek.e_commerce.dto.OrderDtoIU;
import io.github.enesdernek.e_commerce.dto.OrderItemDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.model.Cart;
import io.github.enesdernek.e_commerce.model.CartItem;
import io.github.enesdernek.e_commerce.model.Order;
import io.github.enesdernek.e_commerce.model.OrderItem;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.OrderRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IOrderService;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public OrderDto add(String username ,OrderDtoIU orderDtoIU) throws BadRequestException {

	    Order order = new Order();
	    BeanUtils.copyProperties(orderDtoIU, order);

	    User user = this.userRepository.findByUsername(username);
	            
	    order.setUser(user);

	    Cart cart = this.cartRepository.findById(user.getCart().getCartId())
	            .orElseThrow(() -> new BadRequestException("Cart not found."));

	    List<OrderItem> orderItems = new ArrayList<>();
	    BigDecimal totalPrice = BigDecimal.ZERO;

	    for (CartItem cartItem : new ArrayList<>(cart.getCartItems())) { 

	        Product product = cartItem.getProduct();
	        int quantity = cartItem.getQuantity();

	        if (product.getStockQuantity() < quantity) {
	            throw new BadRequestException("Product '" + product.getName() + "' is out of stock.");
	        }

	        product.setStockQuantity(product.getStockQuantity() - quantity);
	        this.productRepository.save(product);

	        OrderItem orderItem = new OrderItem();
	        orderItem.setProduct(product);
	        orderItem.setQuantity(quantity);
	        orderItem.setOrder(order);
	        orderItems.add(orderItem);

	        BigDecimal itemTotal = product.getDiscountedPrice().multiply(BigDecimal.valueOf(quantity));
	        totalPrice = totalPrice.add(itemTotal);

	        List<Cart> carts = this.cartRepository.findAll();

	        for (Cart c : carts) {
	            Iterator<CartItem> iterator = c.getCartItems().iterator();

	            while (iterator.hasNext()) {
	                CartItem ci = iterator.next();

	                if (ci.getProduct().equals(product) && product.getStockQuantity() == 0) {
	                    if (ci.getQuantity() > 0) {
	                        ci.setQuantity(ci.getQuantity() - quantity);
	                        if (ci.getQuantity() <= 0) {
	                            iterator.remove(); 
	                        }
	                    }
	                }
	            }

	            this.cartRepository.save(c);
	        }
	    }

	    order.setTotalPrice(totalPrice);
	    order.setOrderItems(orderItems);
	    order.setDate(LocalDateTime.now());

	    Order savedOrder = this.orderRepository.save(order);

	    cart.getCartItems().clear();
	    this.cartRepository.save(cart);
	    
	    OrderDto orderDto = new OrderDto();
	    BeanUtils.copyProperties(savedOrder, orderDto);
	    
	    UserDto userDto = new UserDto();
	    BeanUtils.copyProperties(user, userDto);
	    orderDto.setUserDto(userDto);
	    
	    List<OrderItemDto>orderItemDtos = new ArrayList<>();
	    
	    for(OrderItem oi : orderItems) {
	    	OrderItemDto orderItemDto = new OrderItemDto();
	    	BeanUtils.copyProperties(oi, orderItemDto);
	    	
	    	ProductDto productDto = new ProductDto();
	    	BeanUtils.copyProperties(oi.getProduct(), productDto);
	    	
	    	CategoryDto categoryDto = new CategoryDto();
	    	BeanUtils.copyProperties(oi.getProduct().getCategory(), categoryDto);
	    	
	    	productDto.setCategoryDto(categoryDto);
	    	orderItemDto.setProductDto(productDto);
	    	
	    	orderItemDtos.add(orderItemDto);
	    	
	    }
	    
	    orderDto.setOrderItemDtos(orderItemDtos);

	    return orderDto; 
	}



	@Override
	public List<OrderDto>getAllByUsername(String username) {
		
		User user = this.userRepository.findByUsername(username);
		
		List<Order>orders = user.getOrders();
		
		List<OrderDto>orderDtos = new ArrayList<>();
		
		for(Order order:orders) {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(order.getUser(), userDto);
			orderDto.setUserDto(userDto);
			
			List<OrderItemDto>orderItemDtos = new ArrayList<>();
			
			for(OrderItem orderItem:order.getOrderItems()) {
				OrderItemDto orderItemDto = new OrderItemDto();
				BeanUtils.copyProperties(orderItem, orderItemDto);
				
				ProductDto productDto = new ProductDto();
				BeanUtils.copyProperties(orderItem.getProduct(), productDto);
				
				CategoryDto categoryDto = new CategoryDto();
				BeanUtils.copyProperties(orderItem.getProduct().getCategory(), categoryDto);
				productDto.setCategoryDto(categoryDto);
				
				orderItemDto.setProductDto(productDto);
				
				orderItemDtos.add(orderItemDto);
			}
			orderDto.setOrderItemDtos(orderItemDtos);
			orderDtos.add(orderDto);
		}
		
		orderDtos.sort(Comparator.comparing(OrderDto::getOrderId).reversed());
		
		return orderDtos;
	}

	@Override
	public OrderDto getByOrderIdAndUsername(Long orderId,String username) throws BadRequestException{
		
		Order order = this.orderRepository.findById(orderId).get();
		
		if(!order.getUser().getUsername().equals(username)) {
			throw new BadRequestException("This order does not belong to this user: "+username);
		}
		
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		
		List<OrderItemDto>orderItemDtos = new ArrayList<>();
		
		for(OrderItem orderItem:order.getOrderItems()) {
			OrderItemDto orderItemDto = new OrderItemDto();
			BeanUtils.copyProperties(orderItem, orderItemDto);
			
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(orderItem.getProduct(), productDto);
			
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(orderItem.getProduct().getCategory(), categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			orderItemDto.setProductDto(productDto);
			
			orderItemDtos.add(orderItemDto);
		}
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(order.getUser(), userDto);
		orderDto.setUserDto(userDto);
		
		orderDto.setOrderItemDtos(orderItemDtos);
		
		return orderDto;
	}

}
