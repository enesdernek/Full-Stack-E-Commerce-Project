package io.github.enesdernek.e_commerce.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid UserDtoAuthIU userDtoAuthIU) {
		return new ResponseEntity<>(this.userService.authenticate(userDtoAuthIU),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody @Valid UserDtoIU userDtoIU) throws BadRequestException {
		return new ResponseEntity<>(this.userService.register(userDtoIU),HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<UserDto> deleteByUserId(@RequestParam Long userId) {
		return new ResponseEntity<UserDto>(this.userService.deleteByUserId(userId),HttpStatus.OK);
	}
	
	@PostMapping("/add-product-to-favoriteds-list")
	public void addProductToFavoriteListByUserIdAndProductId(@RequestParam Long userId, @RequestParam Long productId) {
	    this.userService.addProductToFavoriteListByUserIdAndProductId(userId, productId);
	}

	@DeleteMapping("/delete-product-from-favoriteds-list")
	public void deleteProductFromFavoriteListByUserIdAndProductId(@RequestParam Long userId, @RequestParam Long productId) {
	    this.userService.deleteProductFromFavoriteListByUserIdAndProductId(userId, productId); // Hatalı metoddan düzeltildi
	}
	
	@GetMapping("/favorited-products")
	public ResponseEntity<List<ProductDto>> getFavoritedProductsListByUserId(@RequestParam Long userId){
		return new ResponseEntity<List<ProductDto>>(this.userService.getFavoritedProductsListByUserId(userId),HttpStatus.OK);
	}
	

}
