package io.github.enesdernek.e_commerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	private ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid UserDtoAuthIU userDtoAuthIU) {
		return new ResponseEntity<>(this.userService.authenticate(userDtoAuthIU),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	private ResponseEntity<UserDto> register(@RequestBody @Valid UserDtoIU userDtoIU) throws BadRequestException {
		return new ResponseEntity<>(this.userService.register(userDtoIU),HttpStatus.OK);
	}
	
	@DeleteMapping
	private ResponseEntity<UserDto> deleteByUserId(@RequestParam Long userId) {
		return new ResponseEntity<UserDto>(this.userService.deleteByUserId(userId),HttpStatus.OK);
	}

}
