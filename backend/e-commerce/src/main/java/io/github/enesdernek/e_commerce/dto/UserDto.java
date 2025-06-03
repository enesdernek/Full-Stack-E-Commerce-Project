package io.github.enesdernek.e_commerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

	private Long userId;

	private String username;
	
	private String email;
	
	private List<ProductDto>favoritedProductDtos;

}
