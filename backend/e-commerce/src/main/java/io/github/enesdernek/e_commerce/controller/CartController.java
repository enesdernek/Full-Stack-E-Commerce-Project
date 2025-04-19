package io.github.enesdernek.e_commerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.dto.CartItemDto;
import io.github.enesdernek.e_commerce.model.User;
import io.github.enesdernek.e_commerce.repository.UserRepository;
import io.github.enesdernek.e_commerce.service.concretes.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	@PostMapping
	public ResponseEntity<CartDto> addProductToCart(@RequestParam Long productId,@RequestParam int quantity,Authentication authentication) throws BadRequestException{
		
		String username = (String) authentication.getName();
		       
		return new ResponseEntity<CartDto>(this.cartService.addProductToCart(username,productId,quantity),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<CartDto>  getCartByUser(Authentication authentication) {
		
		String username = (String) authentication.getName();
		
		return new ResponseEntity<CartDto>(this.cartService.getCartByUsername(username),HttpStatus.OK);
	}
	
	@PutMapping("/change-item-quantity")
	public ResponseEntity<CartDto> changeItemQuantity(@RequestParam Long cartItemId,@RequestParam int quantity,Authentication authentication){
			
		String username = (String) authentication.getName();
		
		return new ResponseEntity<CartDto>(this.cartService.changeItemQuantity(username, cartItemId, quantity),HttpStatus.OK);
	}
	
	@DeleteMapping
	public void deleteAllItems(Authentication authentication) {
		
		String username = (String) authentication.getName();
		
		this.cartService.deleteAllItemsByUsername(username);
	}
     
}
