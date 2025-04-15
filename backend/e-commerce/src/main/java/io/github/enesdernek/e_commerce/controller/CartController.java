package io.github.enesdernek.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.CartDto;
import io.github.enesdernek.e_commerce.service.concretes.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping
	public ResponseEntity<CartDto> addProductToCart(@RequestParam Long cartId,@RequestParam Long productId) {
		return new ResponseEntity<CartDto>(this.cartService.addProductToCart(cartId, productId),HttpStatus.OK);
	}
	
	@GetMapping("/{cartId}")
	public ResponseEntity<CartDto>  getCartByCartId(@PathVariable Long cartId) {
		return new ResponseEntity<CartDto>(this.cartService.getCartByCartId(cartId),HttpStatus.OK);
	}
     
}
