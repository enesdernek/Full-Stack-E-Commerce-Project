package io.github.enesdernek.e_commerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDtoWithProductDetails {

	private Long categoryId;

	private String name;

	private List<ProductDto> products;

}
