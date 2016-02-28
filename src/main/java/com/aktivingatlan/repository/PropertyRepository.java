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
	
    @Query("select property from Property property where property.user.login = ?#{principal.username}")
    List<Property> findByUserIsCurrentUser();

    @Query("select distinct property from Property property left join fetch property.features")
    List<Property> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.features where property.id = :id")
    Property findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select property from Property property where property.code = :code")
    List<Property> findByCode(@Param("code") String code);
    
    List<Property> findByCodeContainingIgnoreCase(String code);
    
    List<Property> findByFeaturedIsTrue();
    
    @Query("select property from Property property where " + 
    "(null = :cityId or property.city.id = :cityId) " + 
    "and (null = :code or property.code like :code) " + 
    "and (null = :categoryId or property.category.id = :categoryId)")
    List<Property> findBySearchParameters(@Param("cityId") Long cityId, @Param("categoryId") Long categoryId, @Param("code") String code);
    
}
