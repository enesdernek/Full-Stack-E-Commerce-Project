package io.github.enesdernek.e_commerce.service.concretes;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CartItemDto;
import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.model.Cart;
import io.github.enesdernek.e_commerce.model.CartItem;
import io.github.enesdernek.e_commerce.repository.CartItemRepository;
import io.github.enesdernek.e_commerce.repository.CartRepository;
import io.github.enesdernek.e_commerce.service.abstracts.ICartItemService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CartItemService implements ICartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired CartRepository cartRepository;
	
	
	
	@Override
	public CartItemDto changeItemQuantity(Long cartId, Long cartItemId,int quantity) {
			
		 if (quantity < 0) {
		        throw new IllegalArgumentException("Quantity cannot be less than 0");
		    }

		    CartItem cartItem = cartItemRepository.findById(cartItemId)
		            .orElseThrow(() -> new EntityNotFoundException("CartItem not found"));

		    Cart cart = cartRepository.findById(cartId)
		            .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

		    if (quantity == 0) {
		      
		        cart.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
		        cartItemRepository.deleteById(cartItemId);
		        
		    } else {
		       
		        cartItem.setQuantity(quantity);
		        cartItemRepository.save(cartItem);
		    }

		    BigDecimal newTotalPrice = BigDecimal.ZERO;
		    List<CartItem> cartItems = cart.getCartItems();

		    for (CartItem item : cartItems) {
		        BigDecimal price = item.getProduct().getPrice(); // BigDecimal
		        BigDecimal qty = BigDecimal.valueOf(item.getQuantity());
		        newTotalPrice = newTotalPrice.add(price.multiply(qty));
		    }

		    cart.setTotalPrice(newTotalPrice);
		    cartRepository.save(cart);
		    
		    CartItemDto cartItemDto = new CartItemDto();
		    BeanUtils.copyProperties(cartItem, cartItemDto);
		    
		    ProductDto productDto = new ProductDto();
		    BeanUtils.copyProperties(cartItem.getProduct(),productDto);
		    
		    CategoryDto categoryDto = new CategoryDto();
		    BeanUtils.copyProperties(cartItem.getProduct().getCategory(), categoryDto);
		    
		    productDto.setCategoryDto(categoryDto);
		    cartItemDto.setProductDto(productDto);
		    
		    return cartItemDto;
			
	}


}
