package com.aktivingatlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Category;

/**
 * Spring Data JPA repository for the Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
