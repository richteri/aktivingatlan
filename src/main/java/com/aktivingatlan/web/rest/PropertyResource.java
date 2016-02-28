package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Photo;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.repository.PhotoRepository;
import com.aktivingatlan.repository.PropertyRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.cloudinary.Cloudinary;
import com.aktivingatlan.web.rest.dto.PropertyDTO;
import com.aktivingatlan.web.rest.mapper.PhotoMapper;
import com.aktivingatlan.web.rest.mapper.PropertyDetailsMapper;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private PropertyDetailsMapper propertyMapper;
    
    @Inject
    private PhotoRepository photoRepository;
    
    @Inject 
    private Cloudinary cloudinary;

    
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
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property", "idexists", "A new property cannot already have an ID")).body(null);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        property = propertyRepository.save(property);
        PropertyDTO result = propertyMapper.propertyToPropertyDTO(property);
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
    @Transactional
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO) throws URISyntaxException {
        log.debug("REST request to update Property : {}", propertyDTO);
        if (propertyDTO.getId() == null) {
            return createProperty(propertyDTO);
        }
        Property property = propertyMapper.propertyDTOToProperty(propertyDTO);
        property = propertyRepository.save(property);
        PropertyDTO result = propertyMapper.propertyToPropertyDTO(property);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("property", propertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /propertys -> get all the propertys.
     */
    @RequestMapping(value = "/propertys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PropertyDTO>> getAllPropertys()
        throws URISyntaxException {
        log.debug("REST request to get a page of Propertys");
        List<Property> page = propertyRepository.findAll(); 
        return new ResponseEntity<>(page.stream()
            .map(propertyMapper::propertyToPropertyDTO)
            .collect(Collectors.toCollection(LinkedList::new)), null, HttpStatus.OK);
    }

    /**
     * GET  /propertys/:id -> get the "id" property.
     */
    @RequestMapping(value = "/propertys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<PropertyDTO> getProperty(@PathVariable Long id) {
        log.debug("REST request to get Property : {}", id);
        Property property = propertyRepository.findOneWithEagerRelationships(id);
        PropertyDTO propertyDTO = propertyMapper.propertyToPropertyDTO(property);
        return Optional.ofNullable(propertyDTO)
            .map(result -> new ResponseEntity<>(
                result,
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
    @Transactional
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        log.debug("REST request to delete Property : {}", id);
        Optional<Property> property = Optional.of(propertyRepository.findOne(id));
        if (property.isPresent()) {
            try {
                // cloudinary.api().deleteResourcesByTag("ID" + property.get().getId(), null);
                if (property.get().getPhotos() != null && !property.get().getPhotos().isEmpty()) {
                	// check if any photo was present
                	// delete by name instead of tag
                	cloudinary.api().deleteResources(property.get().getPhotos().stream().map(Photo::getFilename).collect(Collectors.toSet()), null);
                	photoRepository.delete(property.get().getPhotos());
                }
                propertyRepository.delete(id);
                return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("property", id.toString())).build();
            } catch (Exception e) {
                log.error("Cannot delete photo {} because {}", id, e.getMessage());
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("property", "deleteError", e.getMessage())).body(null);
            }
        } else {
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert("property", "deleteError", "Not found")).build();
        }
    }

    /**
     * SEARCH  /_search/propertys/:query -> search for the property corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/propertys/findByCode/{code}",
            method = { RequestMethod.GET },
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PropertyDTO>> findByCode(@PathVariable String code) {
        return new ResponseEntity<>(propertyRepository.findByCodeContainingIgnoreCase(code).stream()
                .map(propertyMapper::propertyToPropertyDTO)
                .collect(Collectors.toCollection(LinkedList::new)), null, HttpStatus.OK);
    }
    
}
