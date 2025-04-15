package io.github.enesdernek.e_commerce.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtoIU {
	
	@Size(min=2,max=128)
	private String name;
	
	@Size(min=2,max=128)
	private String description;

	@DecimalMax(value = "99.0")
	@DecimalMin(value = "0.0")
	private double discount;
	
	@PositiveOrZero
	private BigDecimal price;
	
	@PositiveOrZero
	private int stockQuantity;
	
	
	private String brand;	
	
	@PositiveOrZero
	private int favCount;
	
	
	private List<String> imagePaths;
	
	
	private Long categoryId;

}
