package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.repository.PropertyRepository;
import com.aktivingatlan.repository.search.PropertySearchRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.aktivingatlan.web.rest.dto.PropertyDTO;
import com.aktivingatlan.web.rest.mapper.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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

    @Inject
    private PropertySearchRepository propertySearchRepository;

    /**
     * POST  /propertys -> Create a new property.
     */
    @RequestMapping(value = "/propertys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PropertyDTO> create(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to save Property : {}", propertyDTO);
        if (propertyDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new property cannot already have an ID").body(null);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        Property result = propertyRepository.save(property);
        propertySearchRepository.save(result);
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
    public ResponseEntity<PropertyDTO> update(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            return create(propertyDTO);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        Property result = propertyRepository.save(property);
        propertySearchRepository.save(property);
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
    public ResponseEntity<List<PropertyDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Property> page = propertyRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/propertys", offset, limit);
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        propertyRepository.delete(id);
        propertySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("property", id.toString())).build();
    }

    /**
     * SEARCH  /_search/propertys/:query -> search for the property corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/propertys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Property> search(@PathVariable String query) {
        return StreamSupport
            .stream(propertySearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
