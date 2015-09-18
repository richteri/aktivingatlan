package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.repository.PropertyRepository;
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
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    @Inject
    private PropertyRepository propertyRepository;

    /**
     * POST  /propertys -> Create a new property.
     */
    @RequestMapping(value = "/propertys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Property> create(@RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to save Property : {}", property);
        if (property.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new property cannot already have an ID").body(null);
        }
        Property result = propertyRepository.save(property);
        return ResponseEntity.created(new URI("/api/propertys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("property", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /propertys -> Updates an existing property.
     */
    @RequestMapping(value = "/propertys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Property> update(@RequestBody Property property) throws URISyntaxException {
        log.debug("REST request to update Property : {}", property);
        if (property.getId() == null) {
            return create(property);
        }
        Property result = propertyRepository.save(property);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("property", property.getId().toString()))
                .body(result);
    }

    /**
     * GET  /propertys -> get all the propertys.
     */
    @RequestMapping(value = "/propertys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Property>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Property> page = propertyRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/propertys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /propertys/:id -> get the "id" property.
     */
    @RequestMapping(value = "/propertys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Property> get(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        return Optional.ofNullable(propertyRepository.findOneWithEagerRelationships(id))
            .map(property -> new ResponseEntity<>(
                property,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /propertys/:id -> delete the "id" property.
     */
    @RequestMapping(value = "/propertys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("property", id.toString())).build();
    }
}