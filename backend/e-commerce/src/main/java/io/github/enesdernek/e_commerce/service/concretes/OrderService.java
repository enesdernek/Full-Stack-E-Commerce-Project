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
		BeanUtils.copyProperties(orderDtoIU, order);
			
		Cart cart = this.cartRepository.findById(cartId).get();
		
		if(cart.getCartItems().size()==0) {
			throw new BadRequestException("Cart can not be empty");
		}
		
		order.setTotalPrice(cart.getTotalPrice());
		
		order.setUser(this.userRepository.findById(userId).get());
		
		List<CartItem> cartItems = new ArrayList<>(cart.getCartItems());
		
		
		order.setCartItems(cartItems);
		
		for (CartItem item : cartItems) {
		    item.setCart(null); // ilişkili cart'ı sıfırla
		}
		cart.getCartItems().clear();

		cart.setTotalPrice(BigDecimal.ZERO);
		this.cartRepository.save(cart);
		
		this.orderRepository.save(order);
		
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
