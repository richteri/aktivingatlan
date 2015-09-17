package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.City;
import com.aktivingatlan.repository.CityRepository;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    @Inject
    private CityRepository cityRepository;

    /**
     * POST  /citys -> Create a new city.
     */
    @RequestMapping(value = "/citys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> create(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new city cannot already have an ID").body(null);
        }
        City result = cityRepository.save(city);
        return ResponseEntity.created(new URI("/api/citys/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("city", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /citys -> Updates an existing city.
     */
    @RequestMapping(value = "/citys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> update(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getId() == null) {
            return create(city);
        }
        City result = cityRepository.save(city);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("city", city.getId().toString()))
                .body(result);
    }

    /**
     * GET  /citys -> get all the citys.
     */
    @RequestMapping(value = "/citys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<City> getAll() {
        log.debug("REST request to get all Citys");
        return cityRepository.findAll();
    }

    /**
     * GET  /citys/:id -> get the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<City> get(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        return Optional.ofNullable(cityRepository.findOne(id))
            .map(city -> new ResponseEntity<>(
                city,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /citys/:id -> delete the "id" city.
     */
    @RequestMapping(value = "/citys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("city", id.toString())).build();
    }
}
