package com.aktivingatlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Feature;

/**
 * Spring Data JPA repository for the Feature entity.
 */
public interface FeatureRepository extends JpaRepository<Feature,Long> {

}
