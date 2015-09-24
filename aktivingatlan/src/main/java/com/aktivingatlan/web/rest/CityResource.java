package com.aktivingatlan.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aktivingatlan.domain.City;
import com.aktivingatlan.repository.CityRepository;
import com.aktivingatlan.repository.search.CitySearchRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    @Inject
    private CityRepository cityRepository;

    @Inject
    private CitySearchRepository citySearchRepository;

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
        citySearchRepository.save(result);
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
        citySearchRepository.save(city);
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
    public ResponseEntity<List<City>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<City> page = cityRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/citys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        citySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("city", id.toString())).build();
    }

    /**
     * SEARCH /_search/citys/:query -> search for the city corresponding to the query.
     * @throws URISyntaxException
     */
    @RequestMapping(value = "/_search/citys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<City>> search(
            @PathVariable String query, 
            @RequestParam(value = "page", required = false) Integer offset, 
            @RequestParam(value = "per_page", required = false) Integer limit,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property) throws URISyntaxException {
        Page<City> page = cityRepository.findByNameContainingOrZipContainingAllIgnoreCase(query, query, 
                PaginationUtil.generatePageRequest(offset, limit, Direction.fromString(direction), property));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/citys", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
