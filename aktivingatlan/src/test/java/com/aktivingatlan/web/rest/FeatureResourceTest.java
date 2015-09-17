package com.aktivingatlan.web.rest;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Feature;
import com.aktivingatlan.repository.FeatureRepository;

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
 * Test class for the FeatureResource REST controller.
 *
 * @see FeatureResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FeatureResourceTest {

    private static final String DEFAULT_NAME_HU = "SAMPLE_TEXT";
    private static final String UPDATED_NAME_HU = "UPDATED_TEXT";
    private static final String DEFAULT_NAME_EN = "SAMPLE_TEXT";
    private static final String UPDATED_NAME_EN = "UPDATED_TEXT";
    private static final String DEFAULT_NAME_DE = "SAMPLE_TEXT";
    private static final String UPDATED_NAME_DE = "UPDATED_TEXT";

    @Inject
    private FeatureRepository featureRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restFeatureMockMvc;

    private Feature feature;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeatureResource featureResource = new FeatureResource();
        ReflectionTestUtils.setField(featureResource, "featureRepository", featureRepository);
        this.restFeatureMockMvc = MockMvcBuilders.standaloneSetup(featureResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        feature = new Feature();
        feature.setNameHu(DEFAULT_NAME_HU);
        feature.setNameEn(DEFAULT_NAME_EN);
        feature.setNameDe(DEFAULT_NAME_DE);
    }

    @Test
    @Transactional
    public void createFeature() throws Exception {
        int databaseSizeBeforeCreate = featureRepository.findAll().size();

        // Create the Feature

        restFeatureMockMvc.perform(post("/api/features")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feature)))
                .andExpect(status().isCreated());

        // Validate the Feature in the database
        List<Feature> features = featureRepository.findAll();
        assertThat(features).hasSize(databaseSizeBeforeCreate + 1);
        Feature testFeature = features.get(features.size() - 1);
        assertThat(testFeature.getNameHu()).isEqualTo(DEFAULT_NAME_HU);
        assertThat(testFeature.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testFeature.getNameDe()).isEqualTo(DEFAULT_NAME_DE);
    }

    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get all the features
        restFeatureMockMvc.perform(get("/api/features"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(feature.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameHu").value(hasItem(DEFAULT_NAME_HU.toString())))
                .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN.toString())))
                .andExpect(jsonPath("$.[*].nameDe").value(hasItem(DEFAULT_NAME_DE.toString())));
    }

    @Test
    @Transactional
    public void getFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", feature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(feature.getId().intValue()))
            .andExpect(jsonPath("$.nameHu").value(DEFAULT_NAME_HU.toString()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN.toString()))
            .andExpect(jsonPath("$.nameDe").value(DEFAULT_NAME_DE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeature() throws Exception {
        // Get the feature
        restFeatureMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

		int databaseSizeBeforeUpdate = featureRepository.findAll().size();

        // Update the feature
        feature.setNameHu(UPDATED_NAME_HU);
        feature.setNameEn(UPDATED_NAME_EN);
        feature.setNameDe(UPDATED_NAME_DE);
        

        restFeatureMockMvc.perform(put("/api/features")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(feature)))
                .andExpect(status().isOk());

        // Validate the Feature in the database
        List<Feature> features = featureRepository.findAll();
        assertThat(features).hasSize(databaseSizeBeforeUpdate);
        Feature testFeature = features.get(features.size() - 1);
        assertThat(testFeature.getNameHu()).isEqualTo(UPDATED_NAME_HU);
        assertThat(testFeature.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testFeature.getNameDe()).isEqualTo(UPDATED_NAME_DE);
    }

    @Test
    @Transactional
    public void deleteFeature() throws Exception {
        // Initialize the database
        featureRepository.saveAndFlush(feature);

		int databaseSizeBeforeDelete = featureRepository.findAll().size();

        // Get the feature
        restFeatureMockMvc.perform(delete("/api/features/{id}", feature.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Feature> features = featureRepository.findAll();
        assertThat(features).hasSize(databaseSizeBeforeDelete - 1);
    }
}
