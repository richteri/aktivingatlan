package com.aktivingatlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aktivingatlan.domain.Property;

/**
 * Spring Data JPA repository for the Property entity.
 */
public interface PropertyRepository extends JpaRepository<Property,Long> {
	
    enum SearchQuery {
    	FIND_BY_CODE
    }

    @Query("select property from Property property where property.user.login = ?#{principal.username}")
    List<Property> findByUserIsCurrentUser();

    @Query("select distinct property from Property property left join fetch property.features")
    List<Property> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.features where property.id = :id")
    Property findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select property from Property property where property.code = :code")
    List<Property> findByCode(@Param("code") String code);
    
    List<Property> findByCodeContainingIgnoreCase(String code);
    
}
