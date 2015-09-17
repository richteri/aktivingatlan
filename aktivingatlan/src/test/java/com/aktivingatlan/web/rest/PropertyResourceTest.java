package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.repository.PropertyRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PropertyResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PropertyResourceTest {

    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION_HU = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION_HU = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION_EN = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION_EN = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION_DE = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION_DE = "UPDATED_TEXT";

    private static final Integer DEFAULT_ROOM = 1;
    private static final Integer UPDATED_ROOM = 2;

    private static final Integer DEFAULT_HALF_ROOM = 1;
    private static final Integer UPDATED_HALF_ROOM = 2;

    private static final Integer DEFAULT_FLOOR_AREA = 1;
    private static final Integer UPDATED_FLOOR_AREA = 2;

    private static final Integer DEFAULT_PARCEL_AREA = 1;
    private static final Integer UPDATED_PARCEL_AREA = 2;
    private static final String DEFAULT_PRACEL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_PRACEL_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS1 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS1 = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS2 = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS2 = "UPDATED_TEXT";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Integer DEFAULT_KITCHEN = 1;
    private static final Integer UPDATED_KITCHEN = 2;

    private static final Integer DEFAULT_LIVINGROOM = 1;
    private static final Integer UPDATED_LIVINGROOM = 2;

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;

    private static final Integer DEFAULT_BATHROOM = 1;
    private static final Integer UPDATED_BATHROOM = 2;

    private static final Integer DEFAULT_TOILET = 1;
    private static final Integer UPDATED_TOILET = 2;

    private static final Boolean DEFAULT_FURNISHED = false;
    private static final Boolean UPDATED_FURNISHED = true;

    private static final Boolean DEFAULT_FOR_SALE = false;
    private static final Boolean UPDATED_FOR_SALE = true;

    private static final BigDecimal DEFAULT_SALE_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALE_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SALE_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALE_EUR = new BigDecimal(2);

    private static final Boolean DEFAULT_FOR_RENT = false;
    private static final Boolean UPDATED_FOR_RENT = true;

    private static final BigDecimal DEFAULT_RENT_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_EUR = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_PEAK_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_PEAK_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RENT_PEAK_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_RENT_PEAK_EUR = new BigDecimal(2);

    private static final Boolean DEFAULT_FOR_MEDIUM_TERM = false;
    private static final Boolean UPDATED_FOR_MEDIUM_TERM = true;

    private static final BigDecimal DEFAULT_MEDIUM_TERM_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDIUM_TERM_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MEDIUM_TERM_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_MEDIUM_TERM_EUR = new BigDecimal(2);

    private static final Boolean DEFAULT_FOR_LONG_TERM = false;
    private static final Boolean UPDATED_FOR_LONG_TERM = true;

    private static final BigDecimal DEFAULT_LONG_TERM_HUF = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONG_TERM_HUF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONG_TERM_EUR = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONG_TERM_EUR = new BigDecimal(2);

    private static final Boolean DEFAULT_FEATURED = false;
    private static final Boolean UPDATED_FEATURED = true;

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPropertyMockMvc;

    private Property property;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropertyResource propertyResource = new PropertyResource();
        ReflectionTestUtils.setField(propertyResource, "propertyRepository", propertyRepository);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        property = new Property();
        property.setCode(DEFAULT_CODE);
        property.setDescriptionHu(DEFAULT_DESCRIPTION_HU);
        property.setDescriptionEn(DEFAULT_DESCRIPTION_EN);
        property.setDescriptionDe(DEFAULT_DESCRIPTION_DE);
        property.setRoom(DEFAULT_ROOM);
        property.setHalfRoom(DEFAULT_HALF_ROOM);
        property.setFloorArea(DEFAULT_FLOOR_AREA);
        property.setParcelArea(DEFAULT_PARCEL_AREA);
        property.setPracelNumber(DEFAULT_PRACEL_NUMBER);
        property.setAddress1(DEFAULT_ADDRESS1);
        property.setAddress2(DEFAULT_ADDRESS2);
        property.setActive(DEFAULT_ACTIVE);
        property.setKitchen(DEFAULT_KITCHEN);
        property.setLivingroom(DEFAULT_LIVINGROOM);
        property.setFloor(DEFAULT_FLOOR);
        property.setBathroom(DEFAULT_BATHROOM);
        property.setToilet(DEFAULT_TOILET);
        property.setFurnished(DEFAULT_FURNISHED);
        property.setForSale(DEFAULT_FOR_SALE);
        property.setSaleHuf(DEFAULT_SALE_HUF);
        property.setSaleEur(DEFAULT_SALE_EUR);
        property.setForRent(DEFAULT_FOR_RENT);
        property.setRentHuf(DEFAULT_RENT_HUF);
        property.setRentEur(DEFAULT_RENT_EUR);
        property.setRentPeakHuf(DEFAULT_RENT_PEAK_HUF);
        property.setRentPeakEur(DEFAULT_RENT_PEAK_EUR);
        property.setForMediumTerm(DEFAULT_FOR_MEDIUM_TERM);
        property.setMediumTermHuf(DEFAULT_MEDIUM_TERM_HUF);
        property.setMediumTermEur(DEFAULT_MEDIUM_TERM_EUR);
        property.setForLongTerm(DEFAULT_FOR_LONG_TERM);
        property.setLongTermHuf(DEFAULT_LONG_TERM_HUF);
        property.setLongTermEur(DEFAULT_LONG_TERM_EUR);
        property.setFeatured(DEFAULT_FEATURED);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property

        restPropertyMockMvc.perform(post("/api/propertys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(property)))
                .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertys = propertyRepository.findAll();
        assertThat(propertys).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertys.get(propertys.size() - 1);
        assertThat(testProperty.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProperty.getDescriptionHu()).isEqualTo(DEFAULT_DESCRIPTION_HU);
        assertThat(testProperty.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testProperty.getDescriptionDe()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testProperty.getRoom()).isEqualTo(DEFAULT_ROOM);
        assertThat(testProperty.getHalfRoom()).isEqualTo(DEFAULT_HALF_ROOM);
        assertThat(testProperty.getFloorArea()).isEqualTo(DEFAULT_FLOOR_AREA);
        assertThat(testProperty.getParcelArea()).isEqualTo(DEFAULT_PARCEL_AREA);
        assertThat(testProperty.getPracelNumber()).isEqualTo(DEFAULT_PRACEL_NUMBER);
        assertThat(testProperty.getAddress1()).isEqualTo(DEFAULT_ADDRESS1);
        assertThat(testProperty.getAddress2()).isEqualTo(DEFAULT_ADDRESS2);
        assertThat(testProperty.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testProperty.getKitchen()).isEqualTo(DEFAULT_KITCHEN);
        assertThat(testProperty.getLivingroom()).isEqualTo(DEFAULT_LIVINGROOM);
        assertThat(testProperty.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testProperty.getBathroom()).isEqualTo(DEFAULT_BATHROOM);
        assertThat(testProperty.getToilet()).isEqualTo(DEFAULT_TOILET);
        assertThat(testProperty.getFurnished()).isEqualTo(DEFAULT_FURNISHED);
        assertThat(testProperty.getForSale()).isEqualTo(DEFAULT_FOR_SALE);
        assertThat(testProperty.getSaleHuf()).isEqualTo(DEFAULT_SALE_HUF);
        assertThat(testProperty.getSaleEur()).isEqualTo(DEFAULT_SALE_EUR);
        assertThat(testProperty.getForRent()).isEqualTo(DEFAULT_FOR_RENT);
        assertThat(testProperty.getRentHuf()).isEqualTo(DEFAULT_RENT_HUF);
        assertThat(testProperty.getRentEur()).isEqualTo(DEFAULT_RENT_EUR);
        assertThat(testProperty.getRentPeakHuf()).isEqualTo(DEFAULT_RENT_PEAK_HUF);
        assertThat(testProperty.getRentPeakEur()).isEqualTo(DEFAULT_RENT_PEAK_EUR);
        assertThat(testProperty.getForMediumTerm()).isEqualTo(DEFAULT_FOR_MEDIUM_TERM);
        assertThat(testProperty.getMediumTermHuf()).isEqualTo(DEFAULT_MEDIUM_TERM_HUF);
        assertThat(testProperty.getMediumTermEur()).isEqualTo(DEFAULT_MEDIUM_TERM_EUR);
        assertThat(testProperty.getForLongTerm()).isEqualTo(DEFAULT_FOR_LONG_TERM);
        assertThat(testProperty.getLongTermHuf()).isEqualTo(DEFAULT_LONG_TERM_HUF);
        assertThat(testProperty.getLongTermEur()).isEqualTo(DEFAULT_LONG_TERM_EUR);
        assertThat(testProperty.getFeatured()).isEqualTo(DEFAULT_FEATURED);
    }

    @Test
    @Transactional
    public void getAllPropertys() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertys
        restPropertyMockMvc.perform(get("/api/propertys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].descriptionHu").value(hasItem(DEFAULT_DESCRIPTION_HU.toString())))
                .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN.toString())))
                .andExpect(jsonPath("$.[*].descriptionDe").value(hasItem(DEFAULT_DESCRIPTION_DE.toString())))
                .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM)))
                .andExpect(jsonPath("$.[*].halfRoom").value(hasItem(DEFAULT_HALF_ROOM)))
                .andExpect(jsonPath("$.[*].floorArea").value(hasItem(DEFAULT_FLOOR_AREA)))
                .andExpect(jsonPath("$.[*].parcelArea").value(hasItem(DEFAULT_PARCEL_AREA)))
                .andExpect(jsonPath("$.[*].pracelNumber").value(hasItem(DEFAULT_PRACEL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS1.toString())))
                .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS2.toString())))
                .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].kitchen").value(hasItem(DEFAULT_KITCHEN)))
                .andExpect(jsonPath("$.[*].livingroom").value(hasItem(DEFAULT_LIVINGROOM)))
                .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
                .andExpect(jsonPath("$.[*].bathroom").value(hasItem(DEFAULT_BATHROOM)))
                .andExpect(jsonPath("$.[*].toilet").value(hasItem(DEFAULT_TOILET)))
                .andExpect(jsonPath("$.[*].furnished").value(hasItem(DEFAULT_FURNISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].forSale").value(hasItem(DEFAULT_FOR_SALE.booleanValue())))
                .andExpect(jsonPath("$.[*].saleHuf").value(hasItem(DEFAULT_SALE_HUF.intValue())))
                .andExpect(jsonPath("$.[*].saleEur").value(hasItem(DEFAULT_SALE_EUR.intValue())))
                .andExpect(jsonPath("$.[*].forRent").value(hasItem(DEFAULT_FOR_RENT.booleanValue())))
                .andExpect(jsonPath("$.[*].rentHuf").value(hasItem(DEFAULT_RENT_HUF.intValue())))
                .andExpect(jsonPath("$.[*].rentEur").value(hasItem(DEFAULT_RENT_EUR.intValue())))
                .andExpect(jsonPath("$.[*].rentPeakHuf").value(hasItem(DEFAULT_RENT_PEAK_HUF.intValue())))
                .andExpect(jsonPath("$.[*].rentPeakEur").value(hasItem(DEFAULT_RENT_PEAK_EUR.intValue())))
                .andExpect(jsonPath("$.[*].forMediumTerm").value(hasItem(DEFAULT_FOR_MEDIUM_TERM.booleanValue())))
                .andExpect(jsonPath("$.[*].mediumTermHuf").value(hasItem(DEFAULT_MEDIUM_TERM_HUF.intValue())))
                .andExpect(jsonPath("$.[*].mediumTermEur").value(hasItem(DEFAULT_MEDIUM_TERM_EUR.intValue())))
                .andExpect(jsonPath("$.[*].forLongTerm").value(hasItem(DEFAULT_FOR_LONG_TERM.booleanValue())))
                .andExpect(jsonPath("$.[*].longTermHuf").value(hasItem(DEFAULT_LONG_TERM_HUF.intValue())))
                .andExpect(jsonPath("$.[*].longTermEur").value(hasItem(DEFAULT_LONG_TERM_EUR.intValue())))
                .andExpect(jsonPath("$.[*].featured").value(hasItem(DEFAULT_FEATURED.booleanValue())));
    }

    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/propertys/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.descriptionHu").value(DEFAULT_DESCRIPTION_HU.toString()))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN.toString()))
            .andExpect(jsonPath("$.descriptionDe").value(DEFAULT_DESCRIPTION_DE.toString()))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM))
            .andExpect(jsonPath("$.halfRoom").value(DEFAULT_HALF_ROOM))
            .andExpect(jsonPath("$.floorArea").value(DEFAULT_FLOOR_AREA))
            .andExpect(jsonPath("$.parcelArea").value(DEFAULT_PARCEL_AREA))
            .andExpect(jsonPath("$.pracelNumber").value(DEFAULT_PRACEL_NUMBER.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS2.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.kitchen").value(DEFAULT_KITCHEN))
            .andExpect(jsonPath("$.livingroom").value(DEFAULT_LIVINGROOM))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.bathroom").value(DEFAULT_BATHROOM))
            .andExpect(jsonPath("$.toilet").value(DEFAULT_TOILET))
            .andExpect(jsonPath("$.furnished").value(DEFAULT_FURNISHED.booleanValue()))
            .andExpect(jsonPath("$.forSale").value(DEFAULT_FOR_SALE.booleanValue()))
            .andExpect(jsonPath("$.saleHuf").value(DEFAULT_SALE_HUF.intValue()))
            .andExpect(jsonPath("$.saleEur").value(DEFAULT_SALE_EUR.intValue()))
            .andExpect(jsonPath("$.forRent").value(DEFAULT_FOR_RENT.booleanValue()))
            .andExpect(jsonPath("$.rentHuf").value(DEFAULT_RENT_HUF.intValue()))
            .andExpect(jsonPath("$.rentEur").value(DEFAULT_RENT_EUR.intValue()))
            .andExpect(jsonPath("$.rentPeakHuf").value(DEFAULT_RENT_PEAK_HUF.intValue()))
            .andExpect(jsonPath("$.rentPeakEur").value(DEFAULT_RENT_PEAK_EUR.intValue()))
            .andExpect(jsonPath("$.forMediumTerm").value(DEFAULT_FOR_MEDIUM_TERM.booleanValue()))
            .andExpect(jsonPath("$.mediumTermHuf").value(DEFAULT_MEDIUM_TERM_HUF.intValue()))
            .andExpect(jsonPath("$.mediumTermEur").value(DEFAULT_MEDIUM_TERM_EUR.intValue()))
            .andExpect(jsonPath("$.forLongTerm").value(DEFAULT_FOR_LONG_TERM.booleanValue()))
            .andExpect(jsonPath("$.longTermHuf").value(DEFAULT_LONG_TERM_HUF.intValue()))
            .andExpect(jsonPath("$.longTermEur").value(DEFAULT_LONG_TERM_EUR.intValue()))
            .andExpect(jsonPath("$.featured").value(DEFAULT_FEATURED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/propertys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

		int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        property.setCode(UPDATED_CODE);
        property.setDescriptionHu(UPDATED_DESCRIPTION_HU);
        property.setDescriptionEn(UPDATED_DESCRIPTION_EN);
        property.setDescriptionDe(UPDATED_DESCRIPTION_DE);
        property.setRoom(UPDATED_ROOM);
        property.setHalfRoom(UPDATED_HALF_ROOM);
        property.setFloorArea(UPDATED_FLOOR_AREA);
        property.setParcelArea(UPDATED_PARCEL_AREA);
        property.setPracelNumber(UPDATED_PRACEL_NUMBER);
        property.setAddress1(UPDATED_ADDRESS1);
        property.setAddress2(UPDATED_ADDRESS2);
        property.setActive(UPDATED_ACTIVE);
        property.setKitchen(UPDATED_KITCHEN);
        property.setLivingroom(UPDATED_LIVINGROOM);
        property.setFloor(UPDATED_FLOOR);
        property.setBathroom(UPDATED_BATHROOM);
        property.setToilet(UPDATED_TOILET);
        property.setFurnished(UPDATED_FURNISHED);
        property.setForSale(UPDATED_FOR_SALE);
        property.setSaleHuf(UPDATED_SALE_HUF);
        property.setSaleEur(UPDATED_SALE_EUR);
        property.setForRent(UPDATED_FOR_RENT);
        property.setRentHuf(UPDATED_RENT_HUF);
        property.setRentEur(UPDATED_RENT_EUR);
        property.setRentPeakHuf(UPDATED_RENT_PEAK_HUF);
        property.setRentPeakEur(UPDATED_RENT_PEAK_EUR);
        property.setForMediumTerm(UPDATED_FOR_MEDIUM_TERM);
        property.setMediumTermHuf(UPDATED_MEDIUM_TERM_HUF);
        property.setMediumTermEur(UPDATED_MEDIUM_TERM_EUR);
        property.setForLongTerm(UPDATED_FOR_LONG_TERM);
        property.setLongTermHuf(UPDATED_LONG_TERM_HUF);
        property.setLongTermEur(UPDATED_LONG_TERM_EUR);
        property.setFeatured(UPDATED_FEATURED);
        

        restPropertyMockMvc.perform(put("/api/propertys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(property)))
                .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertys = propertyRepository.findAll();
        assertThat(propertys).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertys.get(propertys.size() - 1);
        assertThat(testProperty.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProperty.getDescriptionHu()).isEqualTo(UPDATED_DESCRIPTION_HU);
        assertThat(testProperty.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testProperty.getDescriptionDe()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testProperty.getRoom()).isEqualTo(UPDATED_ROOM);
        assertThat(testProperty.getHalfRoom()).isEqualTo(UPDATED_HALF_ROOM);
        assertThat(testProperty.getFloorArea()).isEqualTo(UPDATED_FLOOR_AREA);
        assertThat(testProperty.getParcelArea()).isEqualTo(UPDATED_PARCEL_AREA);
        assertThat(testProperty.getPracelNumber()).isEqualTo(UPDATED_PRACEL_NUMBER);
        assertThat(testProperty.getAddress1()).isEqualTo(UPDATED_ADDRESS1);
        assertThat(testProperty.getAddress2()).isEqualTo(UPDATED_ADDRESS2);
        assertThat(testProperty.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testProperty.getKitchen()).isEqualTo(UPDATED_KITCHEN);
        assertThat(testProperty.getLivingroom()).isEqualTo(UPDATED_LIVINGROOM);
        assertThat(testProperty.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testProperty.getBathroom()).isEqualTo(UPDATED_BATHROOM);
        assertThat(testProperty.getToilet()).isEqualTo(UPDATED_TOILET);
        assertThat(testProperty.getFurnished()).isEqualTo(UPDATED_FURNISHED);
        assertThat(testProperty.getForSale()).isEqualTo(UPDATED_FOR_SALE);
        assertThat(testProperty.getSaleHuf()).isEqualTo(UPDATED_SALE_HUF);
        assertThat(testProperty.getSaleEur()).isEqualTo(UPDATED_SALE_EUR);
        assertThat(testProperty.getForRent()).isEqualTo(UPDATED_FOR_RENT);
        assertThat(testProperty.getRentHuf()).isEqualTo(UPDATED_RENT_HUF);
        assertThat(testProperty.getRentEur()).isEqualTo(UPDATED_RENT_EUR);
        assertThat(testProperty.getRentPeakHuf()).isEqualTo(UPDATED_RENT_PEAK_HUF);
        assertThat(testProperty.getRentPeakEur()).isEqualTo(UPDATED_RENT_PEAK_EUR);
        assertThat(testProperty.getForMediumTerm()).isEqualTo(UPDATED_FOR_MEDIUM_TERM);
        assertThat(testProperty.getMediumTermHuf()).isEqualTo(UPDATED_MEDIUM_TERM_HUF);
        assertThat(testProperty.getMediumTermEur()).isEqualTo(UPDATED_MEDIUM_TERM_EUR);
        assertThat(testProperty.getForLongTerm()).isEqualTo(UPDATED_FOR_LONG_TERM);
        assertThat(testProperty.getLongTermHuf()).isEqualTo(UPDATED_LONG_TERM_HUF);
        assertThat(testProperty.getLongTermEur()).isEqualTo(UPDATED_LONG_TERM_EUR);
        assertThat(testProperty.getFeatured()).isEqualTo(UPDATED_FEATURED);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

		int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertyMockMvc.perform(delete("/api/propertys/{id}", property.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> propertys = propertyRepository.findAll();
        assertThat(propertys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
