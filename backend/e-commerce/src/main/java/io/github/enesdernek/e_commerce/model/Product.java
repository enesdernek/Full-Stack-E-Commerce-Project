package io.github.enesdernek.e_commerce.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="discount")
	private double discount;
	
	@Column(name="price")
	private BigDecimal price;
	
	@Column(name="stock_quantity")
	private int stockQuantity;
	
	@Column(name="brand")
	private String brand;	
	
	@Column(name="fav_count")
	private int favCount;
	
	@Column(name="image_paths")
	private List<String> imagePaths;
}
