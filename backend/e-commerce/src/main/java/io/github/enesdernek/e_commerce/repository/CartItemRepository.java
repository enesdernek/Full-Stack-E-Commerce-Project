package io.github.enesdernek.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.enesdernek.e_commerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
