package com.aktivingatlan.repository;

import com.aktivingatlan.domain.Ownership;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ownership entity.
 */
public interface OwnershipRepository extends JpaRepository<Ownership,Long> {

}
