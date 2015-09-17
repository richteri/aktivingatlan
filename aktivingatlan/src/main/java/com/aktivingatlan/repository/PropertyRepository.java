package com.aktivingatlan.repository;

import com.aktivingatlan.domain.Property;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Property entity.
 */
public interface PropertyRepository extends JpaRepository<Property,Long> {

    @Query("select property from Property property where property.user.login = ?#{principal.username}")
    List<Property> findByUserIsCurrentUser();

    @Query("select distinct property from Property property left join fetch property.features")
    List<Property> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.features where property.id =:id")
    Property findOneWithEagerRelationships(@Param("id") Long id);

}
