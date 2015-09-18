package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Apartment;
import com.aktivingatlan.repository.ApartmentRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Apartment.
 */
@RestController
@RequestMapping("/api")
public class ApartmentResource {

    private final Logger log = LoggerFactory.getLogger(ApartmentResource.class);

    @Inject
    private ApartmentRepository apartmentRepository;

    /**
     * POST  /apartments -> Create a new apartment.
     */
    @RequestMapping(value = "/apartments",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Apartment> create(@RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to save Apartment : {}", apartment);
        if (apartment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new apartment cannot already have an ID").body(null);
        }
        Apartment result = apartmentRepository.save(apartment);
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
    public ResponseEntity<Apartment> update(@RequestBody Apartment apartment) throws URISyntaxException {
        log.debug("REST request to update Apartment : {}", apartment);
        if (apartment.getId() == null) {
            return create(apartment);
        }
        Apartment result = apartmentRepository.save(apartment);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("apartment", apartment.getId().toString()))
                .body(result);
    }

    /**
     * GET  /apartments -> get all the apartments.
     */
    @RequestMapping(value = "/apartments",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Apartment>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Apartment> page = apartmentRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apartments", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apartments/:id -> get the "id" apartment.
     */
    @RequestMapping(value = "/apartments/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Apartment> get(@PathVariable Long id) {
        log.debug("REST request to get Apartment : {}", id);
        return Optional.ofNullable(apartmentRepository.findOne(id))
            .map(apartment -> new ResponseEntity<>(
                apartment,
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Apartment : {}", id);
        apartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apartment", id.toString())).build();
    }
}
