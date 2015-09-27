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

import com.aktivingatlan.domain.Client;
import com.aktivingatlan.domain.Ownership;
import com.aktivingatlan.repository.ClientRepository;
import com.aktivingatlan.repository.OwnershipRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;


/**
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private OwnershipRepository ownershipRepository;

    /**
     * POST  /clients -> Create a new client.
     */
    @RequestMapping(value = "/clients",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> create(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new client cannot already have an ID").body(null);
        }
        Client result = clientRepository.save(client);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("client", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /clients -> Updates an existing client.
     */
    @RequestMapping(value = "/clients",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> update(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            return create(client);
        }
        Client result = clientRepository.save(client);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("client", client.getId().toString()))
                .body(result);
    }

    /**
     * GET  /clients -> get all the clients.
     */
    @RequestMapping(value = "/clients",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Client>> getAll(
            @RequestParam(value = "page" , required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property)
        throws URISyntaxException {
        Page<Client> page = clientRepository.findAll(
            PaginationUtil.generatePageRequest(offset, limit, Direction.fromString(direction), property));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clients", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id -> get the "id" client.
     */
    @RequestMapping(value = "/clients/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client> get(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        return Optional.ofNullable(clientRepository.findByIdWithDetails(id))
            .map(client -> new ResponseEntity<>(
                client,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clients/:id -> delete the "id" client.
     */
    @RequestMapping(value = "/clients/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("client", id.toString())).build();
    }

    /**
     * SEARCH  /_search/clients/:query -> search for the client corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/clients/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Client>> search(
            @PathVariable String query,
            @RequestParam(value = "page", required = false) Integer offset,
            @RequestParam(value = "per_page", required = false) Integer limit,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property)
        throws URISyntaxException {
        Page<Client> page = clientRepository.findByNameContainingOrPhone1ContainingOrAddress1ContainingOrIdNoContainingAllIgnoreCase(query, query, query, query,
            PaginationUtil.generatePageRequest(offset, limit, Direction.fromString(direction), property));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/_search/clients", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
