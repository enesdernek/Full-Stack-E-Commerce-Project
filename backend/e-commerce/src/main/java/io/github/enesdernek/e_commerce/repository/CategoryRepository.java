package io.github.enesdernek.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.enesdernek.e_commerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
