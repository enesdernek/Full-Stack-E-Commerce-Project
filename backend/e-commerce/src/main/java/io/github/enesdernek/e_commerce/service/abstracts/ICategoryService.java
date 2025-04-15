package io.github.enesdernek.e_commerce.service.abstracts;

import java.util.List;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.CategoryDtoIU;

public interface ICategoryService {
	
	List<CategoryDto>getAll();
	
	CategoryDto getByCategoryId(Long categoryId);
	
	CategoryDto add(CategoryDtoIU categoryDtoIU);
	
	CategoryDto deleteByCategoryId(Long categoryId);
	
	CategoryDto updateByCategoryId(Long categoryId,CategoryDtoIU categoryDtoIU);
	

}
