package io.github.enesdernek.e_commerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;
import io.github.enesdernek.e_commerce.service.concretes.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAll() {
		return new ResponseEntity<List<ProductDto>>(this.productService.getAll(), HttpStatus.OK);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<ProductDto> deleteById(@PathVariable("productId") Long productId) {
		return new ResponseEntity<ProductDto>(this.productService.deleteById(productId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> add(@RequestBody ProductDtoIU productDtoIU) {
		return new ResponseEntity<ProductDto>(this.productService.add(productDtoIU),HttpStatus.OK);
	}
}
