package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Ownership;
import com.aktivingatlan.repository.OwnershipRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.aktivingatlan.web.rest.dto.OwnershipDTO;
import com.aktivingatlan.web.rest.mapper.OwnershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Ownership.
 */
@RestController
@RequestMapping("/api")
public class OwnershipResource {

    private final Logger log = LoggerFactory.getLogger(OwnershipResource.class);
        
    @Inject
    private OwnershipRepository ownershipRepository;
    
    @Inject
    private OwnershipMapper ownershipMapper;
    
    /**
     * POST  /ownerships -> Create a new ownership.
     */
    @RequestMapping(value = "/ownerships",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<OwnershipDTO> createOwnership(@RequestBody OwnershipDTO ownershipDTO) throws URISyntaxException {
        log.debug("REST request to save Ownership : {}", ownershipDTO);
        if (ownershipDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ownership", "idexists", "A new ownership cannot already have an ID")).body(null);
        }
        Ownership ownership = ownershipMapper.ownershipDTOToOwnership(ownershipDTO);
        ownership = ownershipRepository.save(ownership);
        
        OwnershipDTO result = ownershipMapper.ownershipToOwnershipDTO(ownership);
        return ResponseEntity.created(new URI("/api/ownerships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ownership", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ownerships -> Updates an existing ownership.
     */
    @RequestMapping(value = "/ownerships",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<OwnershipDTO> updateOwnership(@RequestBody OwnershipDTO ownershipDTO) throws URISyntaxException {
        log.debug("REST request to update Ownership : {}", ownershipDTO);
        if (ownershipDTO.getId() == null) {
            return createOwnership(ownershipDTO);
        }
        Ownership ownership = ownershipMapper.ownershipDTOToOwnership(ownershipDTO);
        ownership = ownershipRepository.save(ownership);
        OwnershipDTO result = ownershipMapper.ownershipToOwnershipDTO(ownership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ownership", ownershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ownerships -> get all the ownerships.
     */
    @RequestMapping(value = "/ownerships",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OwnershipDTO>> getAllOwnerships(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ownerships");
        Page<Ownership> page = ownershipRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ownerships");
        return new ResponseEntity<>(page.getContent().stream()
            .map(ownershipMapper::ownershipToOwnershipDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /ownerships/:id -> get the "id" ownership.
     */
    @RequestMapping(value = "/ownerships/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<OwnershipDTO> getOwnership(@PathVariable Long id) {
        log.debug("REST request to get Ownership : {}", id);
        Ownership ownership = ownershipRepository.findOne(id);
        OwnershipDTO ownershipDTO = ownershipMapper.ownershipToOwnershipDTO(ownership);
        return Optional.ofNullable(ownershipDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ownerships/:id -> delete the "id" ownership.
     */
    @RequestMapping(value = "/ownerships/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOwnership(@PathVariable Long id) {
        log.debug("REST request to delete Ownership : {}", id);
        ownershipRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ownership", id.toString())).build();
    }

    /**
     * SEARCH  /_search/ownerships/:query -> search for the ownership corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/ownerships/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public List<Ownership> search(@PathVariable String query) {
        return StreamSupport
            .stream(ownershipRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
