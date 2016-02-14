package com.aktivingatlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aktivingatlan.domain.Contract;

/**
 * Spring Data JPA repository for the Contract entity.
 */
public interface ContractRepository extends JpaRepository<Contract,Long> {

    @Query("select distinct contract from Contract contract left join fetch contract.clients")
    List<Contract> findAllWithEagerRelationships();

    @Query("select contract from Contract contract left join fetch contract.clients where contract.id =:id")
    Contract findOneWithEagerRelationships(@Param("id") Long id);

}
