package io.github.enesdernek.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.CartItemDto;
import io.github.enesdernek.e_commerce.service.concretes.CartItemService;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@PutMapping("/change-item-quantity")
	public ResponseEntity<CartItemDto> changeItemQuantity(@RequestParam Long cartId,@RequestParam Long cartItemId,@RequestParam int quantity){
		return new ResponseEntity<CartItemDto>(this.cartItemService.changeItemQuantity(cartId, cartItemId, quantity),HttpStatus.OK);
	}

}
