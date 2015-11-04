package com.aktivingatlan.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aktivingatlan.domain.Property;
import com.aktivingatlan.repository.PropertyRepository;
import com.aktivingatlan.web.rest.dto.PropertyDTO;
import com.aktivingatlan.web.rest.mapper.PropertyMapper;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Property.
 */
@RestController
@RequestMapping("/api")
public class PropertyResource {

    private final Logger log = LoggerFactory.getLogger(PropertyResource.class);

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private PropertyMapper propertyMapper;

    /**
     * POST  /propertys -> Create a new property.
     */
    @RequestMapping(value = "/propertys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);
        if (propertyDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new property cannot already have an ID").body(null);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        Property result = propertyRepository.save(property);
        return ResponseEntity.created(new URI("/api/propertys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("property", result.getId().toString()))
                .body(propertyMapper.propertyToPropertyDTO(result));
    }

    /**
     * PUT  /propertys -> Updates an existing property.
     */
    @RequestMapping(value = "/propertys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            return createProperty(propertyDTO);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        Property result = propertyRepository.save(property);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("property", propertyDTO.getId().toString()))
                .body(propertyMapper.propertyToPropertyDTO(result));
    }

    /**
     * GET  /propertys -> get all the propertys.
     */
    @RequestMapping(value = "/propertys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PropertyDTO>> getAllPropertys(Pageable pageable)
        throws URISyntaxException {
        Page<Property> page = propertyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/propertys");
        return new ResponseEntity<>(page.getContent().stream()
            .map(propertyMapper::propertyToPropertyDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /propertys/:id -> get the "id" property.
     */
    @RequestMapping(value = "/propertys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PropertyDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        return Optional.ofNullable(propertyRepository.findOneWithEagerRelationships(id))
            .map(propertyMapper::propertyToPropertyDTO)
            .map(propertyDTO -> new ResponseEntity<>(
                propertyDTO,
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
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("property", id.toString())).build();
    }

    /**
     * SEARCH  /_search/propertys/:query -> search for the property corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/propertys",
        method = {RequestMethod.GET, RequestMethod.GET},
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PropertyDTO>> search(
    		@RequestParam(value = "query", required = false) PropertyRepository.SearchQuery query,
    		@RequestParam(value = "param", required = false) String param) {
    	switch (query) {
        case FIND_BY_CODE:
            return 
                    new ResponseEntity<>(propertyRepository.findByCode(param).stream()
                            .map(propertyMapper::propertyToPropertyDTO)
                            .collect(Collectors.toCollection(LinkedList::new)), HttpStatus.OK);
        case FIND_BY_CODE_CONTAINING:
            return 
                    new ResponseEntity<>(propertyRepository.findByCodeContainingIgnoreCase(param).stream()
                            .map(propertyMapper::propertyToPropertyDTO)
                            .collect(Collectors.toCollection(LinkedList::new)), HttpStatus.OK);
    	default:
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}

    }
}
