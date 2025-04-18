package io.github.enesdernek.e_commerce.dto;

import java.math.BigDecimal;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	
	private Long productId;
	
	
	private String name;
	
	
	private String description;
	
	
	private double discount;
	
	
	private BigDecimal price;
	
	
	private int stockQuantity;
	
	
	private String brand;	
	
	
	private int favCount;
	
	
	private String imagePath;
	
	
	private CategoryDto categoryDto;

}
