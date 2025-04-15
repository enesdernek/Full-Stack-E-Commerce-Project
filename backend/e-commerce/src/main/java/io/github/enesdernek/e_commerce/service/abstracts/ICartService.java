package io.github.enesdernek.e_commerce.service.abstracts;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartDtoIU;

public interface ICartService {
	
	public CartDto addProductToCart(Long cartId, Long productId);
	
	public CartDto getCartByCartId(Long cartId);


}
