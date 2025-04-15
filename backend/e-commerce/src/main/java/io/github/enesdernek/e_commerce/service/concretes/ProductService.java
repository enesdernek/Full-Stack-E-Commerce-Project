package io.github.enesdernek.e_commerce.service.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;
import io.github.enesdernek.e_commerce.exception.NotFoundException;
import io.github.enesdernek.e_commerce.model.Category;
import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.repository.CategoryRepository;
import io.github.enesdernek.e_commerce.repository.ProductRepository;
import io.github.enesdernek.e_commerce.service.abstracts.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public List<ProductDto> getAll() {
		
		List<Product> productsDb = this.productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList();
		
		for(Product product:productsDb) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			Category category = product.getCategory();
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category,categoryDto);
			productDto.setCategoryDto(categoryDto);
			
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
		Category category = product.getCategory();
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category,categoryDto);
		productDto.setCategoryDto(categoryDto);
		this.productRepository.deleteById(productId);
		return productDto;
		
	}

	@Override
	public ProductDto add(ProductDtoIU productDtoIU) {
		
		Product product = new Product();
		Category category = this.categoryRepository.findById(productDtoIU.getCategoryId()).get();
		
		BeanUtils.copyProperties(productDtoIU, product);
		product.setCategory(category);
		this.productRepository.save(product);
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category,categoryDto);
		productDto.setCategoryDto(categoryDto);
		
		return productDto;
	}

	@Override
	public ProductDto updateByProductId(Long productId, ProductDtoIU productDtoIU) {
		
		Product productDb = this.productRepository.findById(productId).get();
		
		BeanUtils.copyProperties(productDtoIU, productDb);
		
		Category category = this.categoryRepository.findById(productDtoIU.getCategoryId()).get();
		
		productDb.setCategory(category);
		
		this.productRepository.save(productDb);
		
		ProductDto productDto = new ProductDto();
		
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category,categoryDto);
		
		BeanUtils.copyProperties(productDb, productDto);
		
		productDto.setCategoryDto(categoryDto);
		
		return productDto;
		
	}

	@Override
	public ProductDto getByProductId(Long productId) {
		Product product = this.productRepository.findById(productId).get();
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		
		Category category = product.getCategory();
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category,categoryDto);
		productDto.setCategoryDto(categoryDto);
		
		return productDto;
	}

	@Override
	public List<ProductDto> searchByNameOrBrandContainsPaged(String searchInput,int pageNo,int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		
		List<Product>filteredProducts = this.productRepository.searchByNameOrBrandContainsPaged(searchInput,pageable);
		
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product product : filteredProducts) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			Category category = product.getCategory();
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category,categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		return productDtos;
	}
	
	public List<ProductDto> getAllPaged(int pageNo,int pageSize){
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<Product> productsDb = this.productRepository.getAllPaged(pageable);
		List<ProductDto> productDtos = new ArrayList();
		
		for(Product product:productsDb) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			Category category = product.getCategory();
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category,categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		
		return productDtos;
	}

	@Override
	public List<ProductDto> getAllByPriceASCPaged(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Product> products = this.productRepository.getAllByPriceASCPaged(pageable);
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product product:products) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			Category category = product.getCategory();
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category,categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		
		return productDtos;
	}

	@Override
	public List<ProductDto> getAllByPriceDESCPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Product> products = this.productRepository.getAllByPriceDESCPaged(pageable);
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product product:products) {
			ProductDto productDto = new ProductDto();
			BeanUtils.copyProperties(product, productDto);
			
			Category category = product.getCategory();
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category,categoryDto);
			productDto.setCategoryDto(categoryDto);
			
			productDtos.add(productDto);
		}
		
		return productDtos;
	}

}
