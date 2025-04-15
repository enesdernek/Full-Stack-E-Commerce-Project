package io.github.enesdernek.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.enesdernek.e_commerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	@Query(value = "SELECT * FROM category ORDER BY category_id DESC",nativeQuery = true)
	List<Category>getAllOrderByCategoryIdDesc();

}
