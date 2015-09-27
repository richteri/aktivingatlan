package com.aktivingatlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Ownership;

/**
 * Spring Data JPA repository for the Ownership entity.
 */
public interface OwnershipRepository extends JpaRepository<Ownership,Long> {
    List<Ownership> findByClientId(Long clientId);
    List<Ownership> findByPropertyId(Long propertyId);
}
