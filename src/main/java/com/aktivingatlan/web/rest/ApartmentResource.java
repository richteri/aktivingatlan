package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Apartment;
import com.aktivingatlan.repository.ApartmentRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.aktivingatlan.web.rest.dto.ApartmentDTO;
import com.aktivingatlan.web.rest.mapper.ApartmentMapper;
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

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Apartment.
 */
@RestController
@RequestMapping("/api")
public class ApartmentResource {

    private final Logger log = LoggerFactory.getLogger(ApartmentResource.class);
        
    @Inject
    private ApartmentRepository apartmentRepository;
    
    @Inject
    private ApartmentMapper apartmentMapper;
    
    /**
     * POST  /apartments -> Create a new apartment.
     */
    @RequestMapping(value = "/apartments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApartmentDTO> createApartment(@RequestBody ApartmentDTO apartmentDTO) throws URISyntaxException {
        log.debug("REST request to save Apartment : {}", apartmentDTO);
        if (apartmentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("apartment", "idexists", "A new apartment cannot already have an ID")).body(null);
        }
        Apartment apartment = apartmentMapper.apartmentDTOToApartment(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        ApartmentDTO result = apartmentMapper.apartmentToApartmentDTO(apartment);
        return ResponseEntity.created(new URI("/api/apartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("apartment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apartments -> Updates an existing apartment.
     */
    @RequestMapping(value = "/apartments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApartmentDTO> updateApartment(@RequestBody ApartmentDTO apartmentDTO) throws URISyntaxException {
        log.debug("REST request to update Apartment : {}", apartmentDTO);
        if (apartmentDTO.getId() == null) {
            return createApartment(apartmentDTO);
        }
        Apartment apartment = apartmentMapper.apartmentDTOToApartment(apartmentDTO);
        apartment = apartmentRepository.save(apartment);
        ApartmentDTO result = apartmentMapper.apartmentToApartmentDTO(apartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apartment", apartmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apartments -> get all the apartments.
     */
    @RequestMapping(value = "/apartments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ApartmentDTO>> getAllApartments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Apartments");
        Page<Apartment> page = apartmentRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apartments");
        return new ResponseEntity<>(page.getContent().stream()
            .map(apartmentMapper::apartmentToApartmentDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /apartments/:id -> get the "id" apartment.
     */
    @RequestMapping(value = "/apartments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApartmentDTO> getApartment(@PathVariable Long id) {
        log.debug("REST request to get Apartment : {}", id);
        Apartment apartment = apartmentRepository.findOne(id);
        ApartmentDTO apartmentDTO = apartmentMapper.apartmentToApartmentDTO(apartment);
        return Optional.ofNullable(apartmentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /apartments/:id -> delete the "id" apartment.
     */
    @RequestMapping(value = "/apartments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        log.debug("REST request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apartment", id.toString())).build();
    }
}
