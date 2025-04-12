package io.github.enesdernek.e_commerce.service.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;
import io.github.enesdernek.e_commerce.exception.NotFoundException;
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

	@Override
	public ProductDto deleteById(Long productId) {
		
		Optional<Product> optionalProduct = this.productRepository.findById(productId);

		if (optionalProduct.isEmpty()) {
		    throw new NotFoundException("There is no product with this id: " + productId);
		}

		Product product = optionalProduct.get();
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		this.productRepository.deleteById(productId);
		return productDto;
		
	}

	@Override
	public ProductDto add(ProductDtoIU productDtoIU) {
		
		Product product = new Product();	
		BeanUtils.copyProperties(productDtoIU, product);
		this.productRepository.save(product);
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		
		return productDto;
	}

}
