package io.github.enesdernek.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.enesdernek.e_commerce.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
}
