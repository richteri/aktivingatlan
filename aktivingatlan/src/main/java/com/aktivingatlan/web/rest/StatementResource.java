package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.repository.StatementRepository;
import com.aktivingatlan.repository.search.StatementSearchRepository;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.aktivingatlan.web.rest.dto.StatementDTO;
import com.aktivingatlan.web.rest.mapper.StatementMapper;
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

    @Inject
    private StatementSearchRepository statementSearchRepository;

    /**
     * POST  /statements -> Create a new statement.
     */
    @RequestMapping(value = "/statements",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatementDTO> create(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
        log.debug("REST request to save Statement : {}", statementDTO);
        if (statementDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new statement cannot already have an ID").body(null);
        }
        Statement statement = statementMapper.statementDTOToStatement(statementDTO);
        Statement result = statementRepository.save(statement);
        statementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/statements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("statement", result.getId().toString()))
                .body(statementMapper.statementToStatementDTO(result));
    }

    /**
     * PUT  /statements -> Updates an existing statement.
     */
    @RequestMapping(value = "/statements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StatementDTO> update(@RequestBody StatementDTO statementDTO) throws URISyntaxException {
        log.debug("REST request to update Statement : {}", statementDTO);
        if (statementDTO.getId() == null) {
            return create(statementDTO);
        }
        Statement statement = statementMapper.statementDTOToStatement(statementDTO);
        Statement result = statementRepository.save(statement);
        statementSearchRepository.save(statement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("statement", statementDTO.getId().toString()))
                .body(statementMapper.statementToStatementDTO(result));
    }

    /**
     * GET  /statements -> get all the statements.
     */
    @RequestMapping(value = "/statements",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<StatementDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Statement> page = statementRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statements", offset, limit);
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
    public ResponseEntity<StatementDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Statement : {}", id);
        return Optional.ofNullable(statementRepository.findOneWithEagerRelationships(id))
            .map(statementMapper::statementToStatementDTO)
            .map(statementDTO -> new ResponseEntity<>(
                statementDTO,
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
        statementSearchRepository.delete(id);
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
            .stream(statementSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
