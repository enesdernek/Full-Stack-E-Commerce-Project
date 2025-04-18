package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.repository.OrderRepository;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IOrderService;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public OrderDto add(Long userId,Long cartId,OrderDtoIU orderDtoIU) throws BadRequestException {
		
	    Order order = new Order();

	    User user = this.userRepository.findById(userId)
	        .orElseThrow(() -> new BadRequestException("User not found."));
	    order.setUser(user);

	    Cart cart = this.cartRepository.findById(cartId)
	        .orElseThrow(() -> new BadRequestException("Cart not found."));

	    order.setTotalPrice(cart.getTotalPrice());

	    // CartItem'ları OrderItem'a çevir
	    List<OrderItem> orderItems = new ArrayList<>();
	    
	    for (CartItem cartItem : cart.getCartItems()) {
	        OrderItem orderItem = new OrderItem();
	        orderItem.setProduct(cartItem.getProduct());
	        orderItem.setQuantity(cartItem.getQuantity());
	        orderItem.setOrder(order);
	        orderItems.add(orderItem);
	    }
	    order.setOrderItems(orderItems);

	    Order savedOrder = this.orderRepository.save(order);

	    cart.getCartItems().clear();
	    cart.setTotalPrice(BigDecimal.ZERO); 
	    this.cartRepository.save(cart);

	    return null; 
	}

	@Override
	public List<OrderDto> getAllByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDto getByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
