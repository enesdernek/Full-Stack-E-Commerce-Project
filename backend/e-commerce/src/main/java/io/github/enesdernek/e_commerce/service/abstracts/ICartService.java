package io.github.enesdernek.e_commerce.service.abstracts;

import org.apache.coyote.BadRequestException;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartDtoIU;
import io.github.enesdernek.e_commerce.dto.CartItemDto;

public interface ICartService {
	
	public CartDto addProductToCart(String username, Long productId, int quantity) throws BadRequestException;
	
	public CartDto getCartByUsername(String username);
	
	public CartDto changeItemQuantity(String username, Long cartItemId,int quantity);
	
	public void deleteAllItemsByUsername(String username);
	



}
