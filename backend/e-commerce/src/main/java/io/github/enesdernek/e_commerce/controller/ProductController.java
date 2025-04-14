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
import io.github.enesdernek.e_commerce.dto.ProductDto;
import io.github.enesdernek.e_commerce.dto.ProductDtoIU;
import io.github.enesdernek.e_commerce.service.concretes.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
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
	
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateByProductId(@PathVariable("productId")Long productId,@RequestBody ProductDtoIU productDtoIU) {
		return new ResponseEntity<ProductDto>(this.productService.updateByProductId(productId, productDtoIU),HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getByProductId(@PathVariable Long productId) {
		return new ResponseEntity<ProductDto>(this.productService.getByProductId(productId),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductDto>> searchByNameOrBrandContainsPaged(@RequestParam String searchInput,@RequestParam int pageNo,@RequestParam int pageSize){
		return new ResponseEntity<List<ProductDto>>(this.productService.searchByNameOrBrandContainsPaged(searchInput,pageNo,pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/paged")
	public ResponseEntity<List<ProductDto>> getAllPaged(@RequestParam int pageNo,@RequestParam int pageSize){
		return new ResponseEntity<List<ProductDto>>(this.productService.getAllPaged(pageNo,pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/sorted-by-price-asc")
	public ResponseEntity<List<ProductDto>> getAllByPriceASCPaged(@RequestParam int pageNo,@RequestParam int pageSize){
		return new ResponseEntity<List<ProductDto>>(this.productService.getAllByPriceASCPaged(pageNo,pageSize),HttpStatus.OK);
	}
	
	@GetMapping("/sorted-by-price-desc")
	public ResponseEntity<List<ProductDto>> getAllByPriceDESCPaged(@RequestParam int pageNo,@RequestParam int pageSize){
		return new ResponseEntity<List<ProductDto>>(this.productService.getAllByPriceDESCPaged(pageNo,pageSize),HttpStatus.OK);
	}
}
