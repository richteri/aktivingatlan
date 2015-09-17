package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.repository.StatementRepository;
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
 * REST controller for managing Statement.
 */
@RestController
@RequestMapping("/api")
public class StatementResource {

    private final Logger log = LoggerFactory.getLogger(StatementResource.class);

    @Inject
    private StatementRepository statementRepository;

    /**
     * POST  /statements -> Create a new statement.
     */
    @RequestMapping(value = "/statements",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statement> create(@RequestBody Statement statement) throws URISyntaxException {
        log.debug("REST request to save Statement : {}", statement);
        if (statement.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statement cannot already have an ID").body(null);
        }
        Statement result = statementRepository.save(statement);
        return ResponseEntity.created(new URI("/api/statements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("statement", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /statements -> Updates an existing statement.
     */
    @RequestMapping(value = "/statements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statement> update(@RequestBody Statement statement) throws URISyntaxException {
        log.debug("REST request to update Statement : {}", statement);
        if (statement.getId() == null) {
            return create(statement);
        }
        Statement result = statementRepository.save(statement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("statement", statement.getId().toString()))
                .body(result);
    }

    /**
     * GET  /statements -> get all the statements.
     */
    @RequestMapping(value = "/statements",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Statement>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Statement> page = statementRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statements", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statements/:id -> get the "id" statement.
     */
    @RequestMapping(value = "/statements/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Statement> get(@PathVariable Long id) {
        log.debug("REST request to get Statement : {}", id);
        return Optional.ofNullable(statementRepository.findOneWithEagerRelationships(id))
            .map(statement -> new ResponseEntity<>(
                statement,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /statements/:id -> delete the "id" statement.
     */
    @RequestMapping(value = "/statements/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Statement : {}", id);
        statementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("statement", id.toString())).build();
    }
}
