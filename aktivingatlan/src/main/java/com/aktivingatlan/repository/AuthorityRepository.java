package com.aktivingatlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktivingatlan.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
