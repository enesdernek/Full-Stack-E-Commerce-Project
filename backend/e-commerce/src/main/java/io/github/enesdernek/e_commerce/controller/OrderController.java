package io.github.enesdernek.e_commerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.OrderDto;
import io.github.enesdernek.e_commerce.dto.OrderDtoIU;
import io.github.enesdernek.e_commerce.service.concretes.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderDto> add(Authentication authentication,@RequestBody OrderDtoIU orderDtoIU) throws BadRequestException{
		
		String username = authentication.getName();
		
		return new ResponseEntity<OrderDto>(this.orderService.add(username,orderDtoIU),HttpStatus.OK);
	}

}
