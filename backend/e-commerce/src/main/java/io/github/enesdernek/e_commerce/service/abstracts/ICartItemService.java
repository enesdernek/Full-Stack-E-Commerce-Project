package io.github.enesdernek.e_commerce.service.abstracts;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartItemDto;

public interface ICartItemService {
	
	public CartItemDto changeItemQuantity(Long cartId, Long cartItemId,int quantity);

}
