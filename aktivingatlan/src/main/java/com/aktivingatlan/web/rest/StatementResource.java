package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.repository.StatementRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.aktivingatlan.web.rest.dto.StatementDTO;
import com.aktivingatlan.web.rest.mapper.StatementMapper;
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

import java.util.stream.StreamSupport;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Statement.
 */
@RestController
@RequestMapping("/api")
public class StatementResource {

    private final Logger log = LoggerFactory.getLogger(StatementResource.class);
        
    @Inject
    private StatementRepository statementRepository;
    
    @Inject
    private StatementMapper statementMapper;
    
    /**
     * POST  /statements -> Create a new statement.
     */
    @RequestMapping(value = "/statements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatementDTO> createStatement(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
        log.debug("REST request to save Statement : {}", statementDTO);
        if (statementDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("statement", "idexists", "A new statement cannot already have an ID")).body(null);
        }
        Statement statement = statementMapper.statementDTOToStatement(statementDTO);
        statement = statementRepository.save(statement);
        StatementDTO result = statementMapper.statementToStatementDTO(statement);
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
    public ResponseEntity<StatementDTO> updateStatement(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
        log.debug("REST request to update Statement : {}", statementDTO);
        if (statementDTO.getId() == null) {
            return createStatement(statementDTO);
        }
        Statement statement = statementMapper.statementDTOToStatement(statementDTO);
        statement = statementRepository.save(statement);
        StatementDTO result = statementMapper.statementToStatementDTO(statement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("statement", statementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statements -> get all the statements.
     */
    @RequestMapping(value = "/statements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StatementDTO>> getAllStatements(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Statements");
        Page<Statement> page = statementRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statements");
        return new ResponseEntity<>(page.getContent().stream()
            .map(statementMapper::statementToStatementDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /statements/:id -> get the "id" statement.
     */
    @RequestMapping(value = "/statements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<StatementDTO> getStatement(@PathVariable Long id) {
        log.debug("REST request to get Statement : {}", id);
        Statement statement = statementRepository.findOneWithEagerRelationships(id);
        StatementDTO statementDTO = statementMapper.statementToStatementDTO(statement);
        return Optional.ofNullable(statementDTO)
            .map(result -> new ResponseEntity<>(
                result,
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
    public ResponseEntity<Void> deleteStatement(@PathVariable Long id) {
        log.debug("REST request to delete Statement : {}", id);
        statementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("statement", id.toString())).build();
    }

    /**
     * SEARCH  /_search/statements/:query -> search for the statement corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/statements/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Statement> search(@PathVariable String query) {
        return StreamSupport
            .stream(statementRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
