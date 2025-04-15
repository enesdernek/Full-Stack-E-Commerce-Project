package io.github.enesdernek.e_commerce.service.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.CategoryDtoIU;
import io.github.enesdernek.e_commerce.model.Category;
import io.github.enesdernek.e_commerce.repository.CategoryRepository;
import io.github.enesdernek.e_commerce.service.abstracts.ICategoryService;

@Service
public class CategoryService implements ICategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getAll() {
		
		List<Category> categories = this.categoryRepository.getAllOrderByCategoryIdDesc();
		List<CategoryDto> categoryDtos = new ArrayList<>();
		
		for(Category category:categories) {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(category, categoryDto);
			categoryDtos.add(categoryDto);
		}
		return categoryDtos;
	}

	@Override
	public CategoryDto getByCategoryId(Long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).get();
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category, categoryDto);
		return categoryDto;
	}

	@Override
	public CategoryDto add(CategoryDtoIU categoryDtoIU) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDtoIU, category);
		
		this.categoryRepository.save(category);
		
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category, categoryDto);
		
        return categoryDto;
	}

	@Override
	public CategoryDto deleteByCategoryId(Long categoryId) {
		
		CategoryDto categoryDto = new CategoryDto();
		Category deletedCategory = this.categoryRepository.findById(categoryId).get();
		BeanUtils.copyProperties(deletedCategory, categoryDto);
		this.categoryRepository.deleteById(categoryId);
		
		return categoryDto;
		
	}

	@Override
	public CategoryDto updateByCategoryId(Long categoryId, CategoryDtoIU categoryDtoIU) {
		Category category = this.categoryRepository.findById(categoryId).get();
		BeanUtils.copyProperties(categoryDtoIU, category);
		this.categoryRepository.save(category);
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category, categoryDto);
		return categoryDto;
	}
	
	

}
