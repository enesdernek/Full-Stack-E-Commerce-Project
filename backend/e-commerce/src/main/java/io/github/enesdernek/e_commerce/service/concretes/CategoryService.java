package io.github.enesdernek.e_commerce.service.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.model.Category;
import io.github.enesdernek.e_commerce.repository.CategoryRepository;
import io.github.enesdernek.e_commerce.service.abstracts.ICategoryService;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getAll() {
		
		List<Category> categories = this.categoryRepository.findAll();
		List<CategoryDto> categoryDtos = new ArrayList<>();
		
		for(Category category:categories) {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category, categoryDto);
			categoryDtos.add(categoryDto);
		}
		return categoryDtos;
	}
	
	

}
