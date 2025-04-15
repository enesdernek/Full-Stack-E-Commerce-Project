package io.github.enesdernek.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.enesdernek.e_commerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
