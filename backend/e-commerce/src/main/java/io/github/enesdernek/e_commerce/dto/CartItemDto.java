package io.github.enesdernek.e_commerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
	
	
	private Long cartItemId;
	
	
	private int quantity;
	
	
    private ProductDto productDto;
	

}
