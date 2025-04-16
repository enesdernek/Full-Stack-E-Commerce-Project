package io.github.enesdernek.e_commerce.dto;

import java.math.BigDecimal;
import java.util.List;

import io.github.enesdernek.e_commerce.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	
	private Long cartId;
	
	private List<CartItemDto> cartItemDtos;
	
	private UserDto userDto;
	
	private BigDecimal totalPrice;

}
