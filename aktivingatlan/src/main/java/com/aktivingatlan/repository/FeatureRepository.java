package com.aktivingatlan.repository;

import com.aktivingatlan.domain.Feature;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Feature entity.
 */
public interface FeatureRepository extends JpaRepository<Feature,Long> {

}
