package com.aktivingatlan.repository;

import com.aktivingatlan.domain.Photo;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Photo entity.
 */
public interface PhotoRepository extends JpaRepository<Photo,Long> {

}
