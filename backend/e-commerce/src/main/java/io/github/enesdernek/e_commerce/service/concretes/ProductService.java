package io.github.enesdernek.e_commerce.service.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	public ProductRepository productRepository;

	public List<ProductDto> getAll() {
		
		List<Product> productsDb = this.productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList();
		
		for(Product product:productsDb) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			productDtos.add(productDto);
		}
		
		return productDtos;
	}

}
