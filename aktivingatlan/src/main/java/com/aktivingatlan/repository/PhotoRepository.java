package com.aktivingatlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Photo;

/**
 * Spring Data JPA repository for the Photo entity.
 */
public interface PhotoRepository extends JpaRepository<Photo,Long> {

}
