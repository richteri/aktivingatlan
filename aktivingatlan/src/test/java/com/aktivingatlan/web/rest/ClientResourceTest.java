package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Client;
import com.aktivingatlan.repository.ClientRepository;
import com.aktivingatlan.repository.search.ClientSearchRepository;
import com.aktivingatlan.web.rest.dto.ClientDTO;
import com.aktivingatlan.web.rest.mapper.ClientDetailsMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Test class for the ClientResource REST controller.
 *
 * @see ClientResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE1 = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE1 = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE2 = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE2 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";
    private static final String DEFAULT_ID_NO = "SAMPLE_TEXT";
    private static final String UPDATED_ID_NO = "UPDATED_TEXT";
    private static final String DEFAULT_NOTE = "SAMPLE_TEXT";
    private static final String UPDATED_NOTE = "UPDATED_TEXT";

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private ClientDetailsMapper clientMapper;

    @Inject
    private ClientSearchRepository clientSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restClientMockMvc;

    private Client client;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientResource clientResource = new ClientResource();
        ReflectionTestUtils.setField(clientResource, "clientRepository", clientRepository);
        ReflectionTestUtils.setField(clientResource, "clientMapper", clientMapper);
        ReflectionTestUtils.setField(clientResource, "clientSearchRepository", clientSearchRepository);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        client = new Client();
        client.setName(DEFAULT_NAME);
        client.setEmail(DEFAULT_EMAIL);
        client.setPhone1(DEFAULT_PHONE1);
        client.setPhone2(DEFAULT_PHONE2);
        client.setAddress1(DEFAULT_ADDRESS1);
        client.setAddress2(DEFAULT_ADDRESS2);
        client.setIdNo(DEFAULT_ID_NO);
        client.setNote(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restClientMockMvc.perform(post("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClient.getPhone1()).isEqualTo(DEFAULT_PHONE1);
        assertThat(testClient.getPhone2()).isEqualTo(DEFAULT_PHONE2);
        assertThat(testClient.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testClient.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testClient.getIdNo()).isEqualTo(DEFAULT_ID_NO);
        assertThat(testClient.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clients
        restClientMockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE1.toString())))
                .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE2.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].idNo").value(hasItem(DEFAULT_ID_NO.toString())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE2.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.idNo").value(DEFAULT_ID_NO.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        client.setName(UPDATED_NAME);
        client.setEmail(UPDATED_EMAIL);
        client.setPhone1(UPDATED_PHONE1);
        client.setPhone2(UPDATED_PHONE2);
        client.setAddress1(UPDATED_ADDRESS1);
        client.setAddress2(UPDATED_ADDRESS2);
        client.setIdNo(UPDATED_ID_NO);
        client.setNote(UPDATED_NOTE);
        
        ClientDTO clientDTO = clientMapper.clientToClientDTO(client);

        restClientMockMvc.perform(put("/api/clients")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
                .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clients.get(clients.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClient.getPhone1()).isEqualTo(UPDATED_PHONE1);
        assertThat(testClient.getPhone2()).isEqualTo(UPDATED_PHONE2);
        assertThat(testClient.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testClient.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testClient.getIdNo()).isEqualTo(UPDATED_ID_NO);
        assertThat(testClient.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

		int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Get the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(databaseSizeBeforeDelete - 1);
    }
}
