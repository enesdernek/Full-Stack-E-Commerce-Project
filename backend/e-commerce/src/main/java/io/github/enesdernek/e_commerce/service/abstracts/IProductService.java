package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;
import io.github.enesdernek.e_commerce.model.Product;

public interface IProductService {
	
	List<ProductDto>getAll();
	
	ProductDto add(ProductDtoIU productDtoIU);
	
	ProductDto deleteById(Long productId);

	ProductDto updateByProductId(Long productId,ProductDtoIU productDtoIU);
	
	ProductDto getByProductId(Long productId);
	
	List<ProductDto> searchByNameOrBrandContainsPaged(String searchInput,int pageNo,int pageSize);
	
    List<ProductDto> getAllPaged(int pageNo,int pageSize);
    
    List<ProductDto> getAllByPriceASCPaged(int pageNo,int pageSize);
    
    List<ProductDto> getAllByPriceDESCPaged(int pageNo,int pageSize);
    
    List<ProductDto> getAllByCategoryIdPaged(Long categoryId,int pageNo,int pageSize);
    
}
