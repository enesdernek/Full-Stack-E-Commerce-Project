package io.github.enesdernek.e_commerce.service.abstracts;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartDtoIU;
import io.github.enesdernek.e_commerce.dto.CartItemDto;

public interface ICartService {
	
	public CartDto addProductToCart(Long cartId, Long productId,int quantity);
	
	public CartDto getCartByCartId(Long cartId);
	
	public CartDto changeItemQuantity(Long cartId, Long cartItemId,int quantity);
	
	public void deleteAllItemsByCartId(Long cartId);
	



}
