package io.github.enesdernek.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.enesdernek.e_commerce.dto.CategoryDto;
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
}
