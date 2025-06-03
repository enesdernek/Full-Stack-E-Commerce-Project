package io.github.enesdernek.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.enesdernek.e_commerce.model.ProductRating;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long>{
	
	List<ProductRating> getByProduct_ProductId(Long productId);

}
