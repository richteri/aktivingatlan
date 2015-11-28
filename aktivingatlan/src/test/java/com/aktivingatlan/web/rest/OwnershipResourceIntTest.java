package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Ownership;
import com.aktivingatlan.repository.OwnershipRepository;
import com.aktivingatlan.web.rest.dto.OwnershipDTO;
import com.aktivingatlan.web.rest.mapper.OwnershipMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OwnershipResource REST controller.
 *
 * @see OwnershipResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OwnershipResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    @Inject
    private OwnershipRepository ownershipRepository;

    @Inject
    private OwnershipMapper ownershipMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOwnershipMockMvc;

    private Ownership ownership;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OwnershipResource ownershipResource = new OwnershipResource();
        ReflectionTestUtils.setField(ownershipResource, "ownershipRepository", ownershipRepository);
        ReflectionTestUtils.setField(ownershipResource, "ownershipMapper", ownershipMapper);
        this.restOwnershipMockMvc = MockMvcBuilders.standaloneSetup(ownershipResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ownership = new Ownership();
        ownership.setNote(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createOwnership() throws Exception {
        int databaseSizeBeforeCreate = ownershipRepository.findAll().size();

        // Create the Ownership
        OwnershipDTO ownershipDTO = ownershipMapper.ownershipToOwnershipDTO(ownership);

        restOwnershipMockMvc.perform(post("/api/ownerships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ownershipDTO)))
                .andExpect(status().isCreated());

        // Validate the Ownership in the database
        List<Ownership> ownerships = ownershipRepository.findAll();
        assertThat(ownerships).hasSize(databaseSizeBeforeCreate + 1);
        Ownership testOwnership = ownerships.get(ownerships.size() - 1);
        assertThat(testOwnership.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void getAllOwnerships() throws Exception {
        // Initialize the database
        ownershipRepository.saveAndFlush(ownership);

        // Get all the ownerships
        restOwnershipMockMvc.perform(get("/api/ownerships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ownership.getId().intValue())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getOwnership() throws Exception {
        // Initialize the database
        ownershipRepository.saveAndFlush(ownership);

        // Get the ownership
        restOwnershipMockMvc.perform(get("/api/ownerships/{id}", ownership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ownership.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOwnership() throws Exception {
        // Get the ownership
        restOwnershipMockMvc.perform(get("/api/ownerships/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnership() throws Exception {
        // Initialize the database
        ownershipRepository.saveAndFlush(ownership);

		int databaseSizeBeforeUpdate = ownershipRepository.findAll().size();

        // Update the ownership
        ownership.setNote(UPDATED_NOTE);
        OwnershipDTO ownershipDTO = ownershipMapper.ownershipToOwnershipDTO(ownership);

        restOwnershipMockMvc.perform(put("/api/ownerships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ownershipDTO)))
                .andExpect(status().isOk());

        // Validate the Ownership in the database
        List<Ownership> ownerships = ownershipRepository.findAll();
        assertThat(ownerships).hasSize(databaseSizeBeforeUpdate);
        Ownership testOwnership = ownerships.get(ownerships.size() - 1);
        assertThat(testOwnership.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void deleteOwnership() throws Exception {
        // Initialize the database
        ownershipRepository.saveAndFlush(ownership);

		int databaseSizeBeforeDelete = ownershipRepository.findAll().size();

        // Get the ownership
        restOwnershipMockMvc.perform(delete("/api/ownerships/{id}", ownership.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ownership> ownerships = ownershipRepository.findAll();
        assertThat(ownerships).hasSize(databaseSizeBeforeDelete - 1);
    }
}
