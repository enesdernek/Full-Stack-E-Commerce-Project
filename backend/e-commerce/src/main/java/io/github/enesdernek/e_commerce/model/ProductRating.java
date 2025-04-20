package io.github.enesdernek.e_commerce.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_rating_id")
	private Long productRatingId;

	private double rating;

	@ManyToOne
	private Product product;
	
	@ManyToOne
	private User user;
}
