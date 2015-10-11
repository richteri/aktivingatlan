package com.aktivingatlan.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aktivingatlan.domain.Contract;
import com.aktivingatlan.repository.ContractRepository;
import com.aktivingatlan.web.rest.dto.ContractDTO;
import com.aktivingatlan.web.rest.mapper.ContractMapper;
import com.aktivingatlan.web.rest.util.HeaderUtil;
import com.aktivingatlan.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing Contract.
 */
@RestController
@RequestMapping("/api")
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);

    @Inject
    private ContractRepository contractRepository;

    @Inject
    private ContractMapper contractMapper;

    /**
     * POST  /contracts -> Create a new contract.
     */
    @RequestMapping(value = "/contracts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContractDTO> create(@RequestBody ContractDTO contractDTO) throws URISyntaxException {
        log.debug("REST request to save Contract : {}", contractDTO);
        if (contractDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new contract cannot already have an ID").body(null);
        }
        Contract contract = contractMapper.contractDTOToContract(contractDTO);
        Contract result = contractRepository.save(contract);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("contract", result.getId().toString()))
                .body(contractMapper.contractToContractDTO(result));
    }

    /**
     * PUT  /contracts -> Updates an existing contract.
     */
    @RequestMapping(value = "/contracts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ContractDTO> update(@RequestBody ContractDTO contractDTO) throws URISyntaxException {
        log.debug("REST request to update Contract : {}", contractDTO);
        if (contractDTO.getId() == null) {
            return create(contractDTO);
        }
        Contract contract = contractMapper.contractDTOToContract(contractDTO);
        Contract result = contractRepository.save(contract);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("contract", contractDTO.getId().toString()))
                .body(contractMapper.contractToContractDTO(result));
    }

    /**
     * GET  /contracts -> get all the contracts.
     */
    @RequestMapping(value = "/contracts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ContractDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Contract> page = contractRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contracts", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(contractMapper::contractToContractDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /contracts/:id -> get the "id" contract.
     */
    @RequestMapping(value = "/contracts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<ContractDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Contract : {}", id);
        return Optional.ofNullable(contractRepository.findOneWithEagerRelationships(id))
            .map(contractMapper::contractToContractDTO)
            .map(contractDTO -> new ResponseEntity<>(
                contractDTO,
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

    /**
     * SEARCH  /_search/contracts/:query -> search for the contract corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/contracts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<Contract> search(@PathVariable String query) {
        return StreamSupport
            .stream(contractRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
