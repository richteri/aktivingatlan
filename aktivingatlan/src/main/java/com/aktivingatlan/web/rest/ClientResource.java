package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Client;
import com.aktivingatlan.repository.ClientRepository;
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
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    @Inject
    private ClientRepository clientRepository;

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
    public ResponseEntity<List<Client>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Client> page = clientRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
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
        return Optional.ofNullable(clientRepository.findOne(id))
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
}
