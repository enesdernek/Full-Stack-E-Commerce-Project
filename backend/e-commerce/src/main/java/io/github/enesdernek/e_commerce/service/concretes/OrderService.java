package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.OrderDto;
import io.github.enesdernek.e_commerce.dto.OrderDtoIU;
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
	public OrderDto add(Long userId, Long cartId, OrderDtoIU orderDtoIU) throws BadRequestException {

	    Order order = new Order();

	    User user = this.userRepository.findById(userId)
	            .orElseThrow(() -> new BadRequestException("User not found."));
	    order.setUser(user);

	    Cart cart = this.cartRepository.findById(cartId)
	            .orElseThrow(() -> new BadRequestException("Cart not found."));

	    List<OrderItem> orderItems = new ArrayList<>();
	    BigDecimal totalPrice = BigDecimal.ZERO;

	    for (CartItem cartItem : new ArrayList<>(cart.getCartItems())) { // ✅ güvenli kopya ile döngü

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
	    Order savedOrder = this.orderRepository.save(order);

	    cart.getCartItems().clear();
	    this.cartRepository.save(cart);

	    return null; 
	}



	@Override
	public List<OrderDto> getAllByUserId(Long userId) {
		
		return null;
	}

	@Override
	public OrderDto getByOrderId(Long orderId) {
		
		return null;
	}

}
