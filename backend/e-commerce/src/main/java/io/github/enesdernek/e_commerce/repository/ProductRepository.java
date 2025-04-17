package io.github.enesdernek.e_commerce.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.enesdernek.e_commerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "SELECT * FROM product WHERE name LIKE %:search% OR brand LIKE %:search%", nativeQuery = true)
	List<Product> searchByNameOrBrandContainsPaged(@Param("search") String searchInput,Pageable pageable);
	
	@Query(value = "SELECT * FROM product ORDER BY product_id DESC",nativeQuery = true)
    List<Product> getAllPaged(Pageable pageable);
	
	@Query(value = "SELECT * FROM product ORDER BY price ASC",nativeQuery = true)
	List<Product> getAllByPriceASCPaged(Pageable pageable);
	
	@Query(value = "SELECT * FROM product ORDER BY price DESC",nativeQuery = true)
	List<Product> getAllByPriceDESCPaged(Pageable pageable);
	
	@Query(value="SELECT * FROM product where category_id =:categoryId ORDER BY product_id desc",nativeQuery = true)
	List<Product> getAllByCategory_CategoryIdOrderByProductIdDescPaged(Long categoryId,Pageable pageable);
}
