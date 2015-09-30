package com.aktivingatlan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aktivingatlan.domain.Client;

/**
 * Spring Data JPA repository for the Client entity.
 */
public interface ClientRepository extends JpaRepository<Client,Long> {
    Page<Client> findByNameContainingOrPhone1ContainingOrAddress1ContainingOrIdNoContainingAllIgnoreCase(String name, String phone, String address, String idNo, Pageable pageable);
    
    @Query("select client from Client client "
            + "left join fetch client.ownerships "
            + "left join fetch client.statements cs "
            + "left join fetch client.contracts "
            + "left join fetch cs.propertys "
            + "where client.id = :id")
    Client findByIdWithEagerRelationships(@Param("id") Long id);
}
