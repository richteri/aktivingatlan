package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.repository.StatementRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the StatementResource REST controller.
 *
 * @see StatementResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StatementResourceTest {


    private static final LocalDate DEFAULT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE = new LocalDate();
    private static final String DEFAULT_NOTE = "SAMPLE_TEXT";
    private static final String UPDATED_NOTE = "UPDATED_TEXT";

    @Inject
    private StatementRepository statementRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restStatementMockMvc;

    private Statement statement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatementResource statementResource = new StatementResource();
        ReflectionTestUtils.setField(statementResource, "statementRepository", statementRepository);
        this.restStatementMockMvc = MockMvcBuilders.standaloneSetup(statementResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        statement = new Statement();
        statement.setDate(DEFAULT_DATE);
        statement.setNote(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createStatement() throws Exception {
        int databaseSizeBeforeCreate = statementRepository.findAll().size();

        // Create the Statement

        restStatementMockMvc.perform(post("/api/statements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statement)))
                .andExpect(status().isCreated());

        // Validate the Statement in the database
        List<Statement> statements = statementRepository.findAll();
        assertThat(statements).hasSize(databaseSizeBeforeCreate + 1);
        Statement testStatement = statements.get(statements.size() - 1);
        assertThat(testStatement.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testStatement.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void getAllStatements() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

        // Get all the statements
        restStatementMockMvc.perform(get("/api/statements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(statement.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

        // Get the statement
        restStatementMockMvc.perform(get("/api/statements/{id}", statement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(statement.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatement() throws Exception {
        // Get the statement
        restStatementMockMvc.perform(get("/api/statements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

		int databaseSizeBeforeUpdate = statementRepository.findAll().size();

        // Update the statement
        statement.setDate(UPDATED_DATE);
        statement.setNote(UPDATED_NOTE);
        

        restStatementMockMvc.perform(put("/api/statements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statement)))
                .andExpect(status().isOk());

        // Validate the Statement in the database
        List<Statement> statements = statementRepository.findAll();
        assertThat(statements).hasSize(databaseSizeBeforeUpdate);
        Statement testStatement = statements.get(statements.size() - 1);
        assertThat(testStatement.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testStatement.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void deleteStatement() throws Exception {
        // Initialize the database
        statementRepository.saveAndFlush(statement);

		int databaseSizeBeforeDelete = statementRepository.findAll().size();

        // Get the statement
        restStatementMockMvc.perform(delete("/api/statements/{id}", statement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Statement> statements = statementRepository.findAll();
        assertThat(statements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
