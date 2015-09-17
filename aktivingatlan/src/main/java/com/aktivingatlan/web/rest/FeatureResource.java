package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Feature;
import com.aktivingatlan.repository.FeatureRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Feature.
 */
@RestController
@RequestMapping("/api")
public class FeatureResource {

    private final Logger log = LoggerFactory.getLogger(FeatureResource.class);

    @Inject
    private FeatureRepository featureRepository;

    /**
     * POST  /features -> Create a new feature.
     */
    @RequestMapping(value = "/features",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feature> create(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to save Feature : {}", feature);
        if (feature.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feature cannot already have an ID").body(null);
        }
        Feature result = featureRepository.save(feature);
        return ResponseEntity.created(new URI("/api/features/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("feature", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /features -> Updates an existing feature.
     */
    @RequestMapping(value = "/features",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feature> update(@RequestBody Feature feature) throws URISyntaxException {
        log.debug("REST request to update Feature : {}", feature);
        if (feature.getId() == null) {
            return create(feature);
        }
        Feature result = featureRepository.save(feature);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("feature", feature.getId().toString()))
                .body(result);
    }

    /**
     * GET  /features -> get all the features.
     */
    @RequestMapping(value = "/features",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Feature> getAll() {
        log.debug("REST request to get all Features");
        return featureRepository.findAll();
    }

    /**
     * GET  /features/:id -> get the "id" feature.
     */
    @RequestMapping(value = "/features/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Feature> get(@PathVariable Long id) {
        log.debug("REST request to get Feature : {}", id);
        return Optional.ofNullable(featureRepository.findOne(id))
            .map(feature -> new ResponseEntity<>(
                feature,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /features/:id -> delete the "id" feature.
     */
    @RequestMapping(value = "/features/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Feature : {}", id);
        featureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feature", id.toString())).build();
    }
}
