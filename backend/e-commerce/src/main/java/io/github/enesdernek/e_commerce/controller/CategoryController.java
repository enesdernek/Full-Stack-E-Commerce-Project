package io.github.enesdernek.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
import io.github.enesdernek.e_commerce.dto.CategoryDtoIU;
import io.github.enesdernek.e_commerce.service.concretes.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
     
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAll(){
		return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getByCategoryId(@PathVariable Long categoryId) {
		return new ResponseEntity<CategoryDto>(this.categoryService.getByCategoryId(categoryId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> add(@RequestBody CategoryDtoIU categoryDtoIU) {
		return new ResponseEntity<CategoryDto>(this.categoryService.add(categoryDtoIU),HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<CategoryDto> deleteByCategoryId(@RequestParam Long categoryId) {
		return new ResponseEntity<CategoryDto>(this.categoryService.deleteByCategoryId(categoryId),HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<CategoryDto> updateByCategoryId(@RequestParam Long categoryId,@RequestBody CategoryDtoIU categoryDtoIU) {
		return new ResponseEntity<CategoryDto>(this.categoryService.updateByCategoryId(categoryId, categoryDtoIU),HttpStatus.OK);
	}
}
