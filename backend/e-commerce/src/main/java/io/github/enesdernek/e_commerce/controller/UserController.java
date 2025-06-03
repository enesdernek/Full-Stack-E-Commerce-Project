package io.github.enesdernek.e_commerce.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.UserDto;
import io.github.enesdernek.e_commerce.dto.UserDtoAuthIU;
import io.github.enesdernek.e_commerce.dto.UserDtoIU;
import io.github.enesdernek.e_commerce.jwt.AuthResponse;
import io.github.enesdernek.e_commerce.service.concretes.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser(Authentication authentication){
		String username = authentication.getName();
		return new ResponseEntity<UserDto>(this.userService.getCurrentUser(username),HttpStatus.OK);
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid UserDtoAuthIU userDtoAuthIU) throws InterruptedException {
		Thread.sleep(500);
		return new ResponseEntity<>(this.userService.authenticate(userDtoAuthIU),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody @Valid UserDtoIU userDtoIU) throws BadRequestException {
		return new ResponseEntity<>(this.userService.register(userDtoIU),HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<UserDto> deleteByUsername(Authentication authentication) {
		
		String username = authentication.getName();
				
		return new ResponseEntity<UserDto>(this.userService.deleteByUsername(username),HttpStatus.OK);
	}
	
	@PostMapping("/add-product-to-favoriteds-list")
	public void addProductToFavoriteListByUserIdAndProductId(Authentication authentication,@RequestParam Long productId) {
		
		String username = authentication.getName();
		
	    this.userService.addProductToFavoriteListByUsernameAndProductId(username, productId);
	}

	@DeleteMapping("/delete-product-from-favoriteds-list")
	public void deleteProductFromFavoriteListByUserIdAndProductId(Authentication authentication, @RequestParam Long productId) {
		
		String username = authentication.getName();
		
	    this.userService.deleteProductFromFavoriteListByUsernameAndProductId(username, productId); 
	}
	
	@GetMapping("/favorited-products")
	public ResponseEntity<List<ProductDto>> getFavoritedProductsList(Authentication authentication){
		
		String username = authentication.getName();
		
		return new ResponseEntity<List<ProductDto>>(this.userService.getFavoritedProductsListByUsername(username),HttpStatus.OK);
	}
	
	@PutMapping("/rate-product")
	public ResponseEntity<ProductDto> rateProductByUsernameAndProductId(Authentication authentication,Long orderId, Long productId,double rating) {
		
		String username = authentication.getName();
		
		return new ResponseEntity<ProductDto>(this.userService.rateProductByUsernameAndProductId(username, orderId, productId, rating),HttpStatus.OK);
		
	}
	
	

}
