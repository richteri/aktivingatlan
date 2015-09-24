package com.aktivingatlan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.City;

/**
 * Spring Data JPA repository for the City entity.
 */
public interface CityRepository extends JpaRepository<City,Long> {
    Page<City> findByNameContainingOrZipContainingAllIgnoreCase(String name, String zip, Pageable pageable);
}
