package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Apartment;
import com.aktivingatlan.repository.ApartmentRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ApartmentResource REST controller.
 *
 * @see ApartmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApartmentResourceIntTest {


    private static final Integer DEFAULT_BED = 1;
    private static final Integer UPDATED_BED = 2;

    private static final Boolean DEFAULT_BATHROOM = false;
    private static final Boolean UPDATED_BATHROOM = true;

    private static final Boolean DEFAULT_TOILET = false;
    private static final Boolean UPDATED_TOILET = true;

    private static final BigDecimal DEFAULT_RENT_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_EUR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_PEAK_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_PEAK_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_PEAK_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_PEAK_EUR = new BigDecimal(2);
    private static final String DEFAULT_DESCRIPTION_HU = "AAAAA";
    private static final String UPDATED_DESCRIPTION_HU = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_EN = "AAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_DE = "AAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBB";

    @Inject
    private ApartmentRepository apartmentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restApartmentMockMvc;

    private Apartment apartment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApartmentResource apartmentResource = new ApartmentResource();
        ReflectionTestUtils.setField(apartmentResource, "apartmentRepository", apartmentRepository);
        this.restApartmentMockMvc = MockMvcBuilders.standaloneSetup(apartmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        apartment = new Apartment();
        apartment.setBed(DEFAULT_BED);
        apartment.setBathroom(DEFAULT_BATHROOM);
        apartment.setToilet(DEFAULT_TOILET);
        apartment.setRentHuf(DEFAULT_RENT_HUF);
        apartment.setRentEur(DEFAULT_RENT_EUR);
        apartment.setRentPeakHuf(DEFAULT_RENT_PEAK_HUF);
        apartment.setRentPeakEur(DEFAULT_RENT_PEAK_EUR);
        apartment.setDescriptionHu(DEFAULT_DESCRIPTION_HU);
        apartment.setDescriptionEn(DEFAULT_DESCRIPTION_EN);
        apartment.setDescriptionDe(DEFAULT_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    public void createApartment() throws Exception {
        int databaseSizeBeforeCreate = apartmentRepository.findAll().size();

        // Create the Apartment

        restApartmentMockMvc.perform(post("/api/apartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apartment)))
                .andExpect(status().isCreated());

        // Validate the Apartment in the database
        List<Apartment> apartments = apartmentRepository.findAll();
        assertThat(apartments).hasSize(databaseSizeBeforeCreate + 1);
        Apartment testApartment = apartments.get(apartments.size() - 1);
        assertThat(testApartment.getBed()).isEqualTo(DEFAULT_BED);
        assertThat(testApartment.getBathroom()).isEqualTo(DEFAULT_BATHROOM);
        assertThat(testApartment.getToilet()).isEqualTo(DEFAULT_TOILET);
        assertThat(testApartment.getRentHuf()).isEqualTo(DEFAULT_RENT_HUF);
        assertThat(testApartment.getRentEur()).isEqualTo(DEFAULT_RENT_EUR);
        assertThat(testApartment.getRentPeakHuf()).isEqualTo(DEFAULT_RENT_PEAK_HUF);
        assertThat(testApartment.getRentPeakEur()).isEqualTo(DEFAULT_RENT_PEAK_EUR);
        assertThat(testApartment.getDescriptionHu()).isEqualTo(DEFAULT_DESCRIPTION_HU);
        assertThat(testApartment.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testApartment.getDescriptionDe()).isEqualTo(DEFAULT_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    public void getAllApartments() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get all the apartments
        restApartmentMockMvc.perform(get("/api/apartments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(apartment.getId().intValue())))
                .andExpect(jsonPath("$.[*].bed").value(hasItem(DEFAULT_BED)))
                .andExpect(jsonPath("$.[*].bathroom").value(hasItem(DEFAULT_BATHROOM.booleanValue())))
                .andExpect(jsonPath("$.[*].toilet").value(hasItem(DEFAULT_TOILET.booleanValue())))
                .andExpect(jsonPath("$.[*].rentHuf").value(hasItem(DEFAULT_RENT_HUF.intValue())))
                .andExpect(jsonPath("$.[*].rentEur").value(hasItem(DEFAULT_RENT_EUR.intValue())))
                .andExpect(jsonPath("$.[*].rentPeakHuf").value(hasItem(DEFAULT_RENT_PEAK_HUF.intValue())))
                .andExpect(jsonPath("$.[*].rentPeakEur").value(hasItem(DEFAULT_RENT_PEAK_EUR.intValue())))
                .andExpect(jsonPath("$.[*].descriptionHu").value(hasItem(DEFAULT_DESCRIPTION_HU.toString())))
                .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN.toString())))
                .andExpect(jsonPath("$.[*].descriptionDe").value(hasItem(DEFAULT_DESCRIPTION_DE.toString())));
    }

    @Test
    @Transactional
    public void getApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", apartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(apartment.getId().intValue()))
            .andExpect(jsonPath("$.bed").value(DEFAULT_BED))
            .andExpect(jsonPath("$.bathroom").value(DEFAULT_BATHROOM.booleanValue()))
            .andExpect(jsonPath("$.toilet").value(DEFAULT_TOILET.booleanValue()))
            .andExpect(jsonPath("$.rentHuf").value(DEFAULT_RENT_HUF.intValue()))
            .andExpect(jsonPath("$.rentEur").value(DEFAULT_RENT_EUR.intValue()))
            .andExpect(jsonPath("$.rentPeakHuf").value(DEFAULT_RENT_PEAK_HUF.intValue()))
            .andExpect(jsonPath("$.rentPeakEur").value(DEFAULT_RENT_PEAK_EUR.intValue()))
            .andExpect(jsonPath("$.descriptionHu").value(DEFAULT_DESCRIPTION_HU.toString()))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN.toString()))
            .andExpect(jsonPath("$.descriptionDe").value(DEFAULT_DESCRIPTION_DE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApartment() throws Exception {
        // Get the apartment
        restApartmentMockMvc.perform(get("/api/apartments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

		int databaseSizeBeforeUpdate = apartmentRepository.findAll().size();

        // Update the apartment
        apartment.setBed(UPDATED_BED);
        apartment.setBathroom(UPDATED_BATHROOM);
        apartment.setToilet(UPDATED_TOILET);
        apartment.setRentHuf(UPDATED_RENT_HUF);
        apartment.setRentEur(UPDATED_RENT_EUR);
        apartment.setRentPeakHuf(UPDATED_RENT_PEAK_HUF);
        apartment.setRentPeakEur(UPDATED_RENT_PEAK_EUR);
        apartment.setDescriptionHu(UPDATED_DESCRIPTION_HU);
        apartment.setDescriptionEn(UPDATED_DESCRIPTION_EN);
        apartment.setDescriptionDe(UPDATED_DESCRIPTION_DE);

        restApartmentMockMvc.perform(put("/api/apartments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(apartment)))
                .andExpect(status().isOk());

        // Validate the Apartment in the database
        List<Apartment> apartments = apartmentRepository.findAll();
        assertThat(apartments).hasSize(databaseSizeBeforeUpdate);
        Apartment testApartment = apartments.get(apartments.size() - 1);
        assertThat(testApartment.getBed()).isEqualTo(UPDATED_BED);
        assertThat(testApartment.getBathroom()).isEqualTo(UPDATED_BATHROOM);
        assertThat(testApartment.getToilet()).isEqualTo(UPDATED_TOILET);
        assertThat(testApartment.getRentHuf()).isEqualTo(UPDATED_RENT_HUF);
        assertThat(testApartment.getRentEur()).isEqualTo(UPDATED_RENT_EUR);
        assertThat(testApartment.getRentPeakHuf()).isEqualTo(UPDATED_RENT_PEAK_HUF);
        assertThat(testApartment.getRentPeakEur()).isEqualTo(UPDATED_RENT_PEAK_EUR);
        assertThat(testApartment.getDescriptionHu()).isEqualTo(UPDATED_DESCRIPTION_HU);
        assertThat(testApartment.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testApartment.getDescriptionDe()).isEqualTo(UPDATED_DESCRIPTION_DE);
    }

    @Test
    @Transactional
    public void deleteApartment() throws Exception {
        // Initialize the database
        apartmentRepository.saveAndFlush(apartment);

		int databaseSizeBeforeDelete = apartmentRepository.findAll().size();

        // Get the apartment
        restApartmentMockMvc.perform(delete("/api/apartments/{id}", apartment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Apartment> apartments = apartmentRepository.findAll();
        assertThat(apartments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
