package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;

public interface IProductService {
	
	List<ProductDto>getAll();
	
	ProductDto deleteById(Long productId);

	ProductDto add(ProductDtoIU productDtoIU);

}
