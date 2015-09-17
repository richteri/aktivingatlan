package com.aktivingatlan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.aktivingatlan.domain.Contract;
import com.aktivingatlan.repository.ContractRepository;
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
 * REST controller for managing Contract.
 */
@RestController
@RequestMapping("/api")
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);

    @Inject
    private ContractRepository contractRepository;

    /**
     * POST  /contracts -> Create a new contract.
     */
    @RequestMapping(value = "/contracts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contract> create(@RequestBody Contract contract) throws URISyntaxException {
        log.debug("REST request to save Contract : {}", contract);
        if (contract.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contract cannot already have an ID").body(null);
        }
        Contract result = contractRepository.save(contract);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("contract", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /contracts -> Updates an existing contract.
     */
    @RequestMapping(value = "/contracts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contract> update(@RequestBody Contract contract) throws URISyntaxException {
        log.debug("REST request to update Contract : {}", contract);
        if (contract.getId() == null) {
            return create(contract);
        }
        Contract result = contractRepository.save(contract);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("contract", contract.getId().toString()))
                .body(result);
    }

    /**
     * GET  /contracts -> get all the contracts.
     */
    @RequestMapping(value = "/contracts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Contract>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Contract> page = contractRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contracts", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contracts/:id -> get the "id" contract.
     */
    @RequestMapping(value = "/contracts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contract> get(@PathVariable Long id) {
        log.debug("REST request to get Contract : {}", id);
        return Optional.ofNullable(contractRepository.findOneWithEagerRelationships(id))
            .map(contract -> new ResponseEntity<>(
                contract,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contracts/:id -> delete the "id" contract.
     */
    @RequestMapping(value = "/contracts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Contract : {}", id);
        contractRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contract", id.toString())).build();
    }
}
