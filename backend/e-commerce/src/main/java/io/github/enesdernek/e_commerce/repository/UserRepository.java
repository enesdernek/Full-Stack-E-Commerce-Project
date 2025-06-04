package io.github.enesdernek.e_commerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.enesdernek.e_commerce.model.Product;
import io.github.enesdernek.e_commerce.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
	boolean existsByUsername(String username);

	@Query("SELECT p FROM Product p JOIN p.usersWhoFavorited u WHERE u.username = :username")
	List<Product> getFavoritedProductsByUsername(@Param("username") String username, Pageable pageable);

	
}
