package com.aktivingatlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Apartment;

/**
 * Spring Data JPA repository for the Apartment entity.
 */
public interface ApartmentRepository extends JpaRepository<Apartment,Long> {

}
