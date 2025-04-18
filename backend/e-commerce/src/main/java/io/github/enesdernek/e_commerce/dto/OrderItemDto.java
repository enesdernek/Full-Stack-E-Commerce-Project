package io.github.enesdernek.e_commerce.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
	
	private Long orderItemId;
	
	private int quantity;
	
    private ProductDto productDto;

}
