package io.github.enesdernek.e_commerce.dto;

import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRatingDto {
	
	private Long productRatingId;

	private double rating;

	private Product product;
	
	private User user;

}
