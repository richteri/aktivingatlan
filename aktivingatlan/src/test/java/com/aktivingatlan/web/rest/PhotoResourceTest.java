package com.aktivingatlan.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.aktivingatlan.Application;
import com.aktivingatlan.domain.Photo;
import com.aktivingatlan.repository.PhotoRepository;
import com.aktivingatlan.web.rest.dto.PhotoDTO;
import com.aktivingatlan.web.rest.mapper.PhotoMapper;


/**
 * Test class for the PhotoResource REST controller.
 *
 * @see PhotoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhotoResourceTest {


    private static final Boolean DEFAULT_HEADER = false;
    private static final Boolean UPDATED_HEADER = true;
    private static final String DEFAULT_DESCRIPTION_HU = "AAAAA";
    private static final String UPDATED_DESCRIPTION_HU = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_EN = "AAAAA";
    private static final String UPDATED_DESCRIPTION_EN = "BBBBB";
    private static final String DEFAULT_DESCRIPTION_DE = "AAAAA";
    private static final String UPDATED_DESCRIPTION_DE = "BBBBB";
    private static final String DEFAULT_FILENAME = "AAAAA";
    private static final String UPDATED_FILENAME = "BBBBB";

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private PhotoMapper photoMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPhotoMockMvc;

    private Photo photo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhotoResource photoResource = new PhotoResource();
        ReflectionTestUtils.setField(photoResource, "photoRepository", photoRepository);
        ReflectionTestUtils.setField(photoResource, "photoMapper", photoMapper);
        this.restPhotoMockMvc = MockMvcBuilders.standaloneSetup(photoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        photo = new Photo();
        photo.setHeader(DEFAULT_HEADER);
        photo.setDescriptionHu(DEFAULT_DESCRIPTION_HU);
        photo.setDescriptionEn(DEFAULT_DESCRIPTION_EN);
        photo.setDescriptionDe(DEFAULT_DESCRIPTION_DE);
        photo.setFilename(DEFAULT_FILENAME);
    }

    @Test
    @Transactional
    public void createPhoto() throws Exception {
        int databaseSizeBeforeCreate = photoRepository.findAll().size();

        // Create the Photo
        PhotoDTO photoDTO = photoMapper.photoToPhotoDTO(photo);

        restPhotoMockMvc.perform(post("/api/photos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
                .andExpect(status().isCreated());

        // Validate the Photo in the database
        List<Photo> photos = photoRepository.findAll();
        assertThat(photos).hasSize(databaseSizeBeforeCreate + 1);
        Photo testPhoto = photos.get(photos.size() - 1);
        assertThat(testPhoto.getHeader()).isEqualTo(DEFAULT_HEADER);
        assertThat(testPhoto.getDescriptionHu()).isEqualTo(DEFAULT_DESCRIPTION_HU);
        assertThat(testPhoto.getDescriptionEn()).isEqualTo(DEFAULT_DESCRIPTION_EN);
        assertThat(testPhoto.getDescriptionDe()).isEqualTo(DEFAULT_DESCRIPTION_DE);
        assertThat(testPhoto.getFilename()).isEqualTo(DEFAULT_FILENAME);
    }

    @Test
    @Transactional
    public void getAllPhotos() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get all the photos
        restPhotoMockMvc.perform(get("/api/photos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(photo.getId().intValue())))
                .andExpect(jsonPath("$.[*].header").value(hasItem(DEFAULT_HEADER.booleanValue())))
                .andExpect(jsonPath("$.[*].descriptionHu").value(hasItem(DEFAULT_DESCRIPTION_HU.toString())))
                .andExpect(jsonPath("$.[*].descriptionEn").value(hasItem(DEFAULT_DESCRIPTION_EN.toString())))
                .andExpect(jsonPath("$.[*].descriptionDe").value(hasItem(DEFAULT_DESCRIPTION_DE.toString())))
                .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())));
    }

    @Test
    @Transactional
    public void getPhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

        // Get the photo
        restPhotoMockMvc.perform(get("/api/photos/{id}", photo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(photo.getId().intValue()))
            .andExpect(jsonPath("$.header").value(DEFAULT_HEADER.booleanValue()))
            .andExpect(jsonPath("$.descriptionHu").value(DEFAULT_DESCRIPTION_HU.toString()))
            .andExpect(jsonPath("$.descriptionEn").value(DEFAULT_DESCRIPTION_EN.toString()))
            .andExpect(jsonPath("$.descriptionDe").value(DEFAULT_DESCRIPTION_DE.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhoto() throws Exception {
        // Get the photo
        restPhotoMockMvc.perform(get("/api/photos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

		int databaseSizeBeforeUpdate = photoRepository.findAll().size();

        // Update the photo
        photo.setHeader(UPDATED_HEADER);
        photo.setDescriptionHu(UPDATED_DESCRIPTION_HU);
        photo.setDescriptionEn(UPDATED_DESCRIPTION_EN);
        photo.setDescriptionDe(UPDATED_DESCRIPTION_DE);
        photo.setFilename(UPDATED_FILENAME);
        PhotoDTO photoDTO = photoMapper.photoToPhotoDTO(photo);

        restPhotoMockMvc.perform(put("/api/photos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(photoDTO)))
                .andExpect(status().isOk());

        // Validate the Photo in the database
        List<Photo> photos = photoRepository.findAll();
        assertThat(photos).hasSize(databaseSizeBeforeUpdate);
        Photo testPhoto = photos.get(photos.size() - 1);
        assertThat(testPhoto.getHeader()).isEqualTo(UPDATED_HEADER);
        assertThat(testPhoto.getDescriptionHu()).isEqualTo(UPDATED_DESCRIPTION_HU);
        assertThat(testPhoto.getDescriptionEn()).isEqualTo(UPDATED_DESCRIPTION_EN);
        assertThat(testPhoto.getDescriptionDe()).isEqualTo(UPDATED_DESCRIPTION_DE);
        assertThat(testPhoto.getFilename()).isEqualTo(UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void deletePhoto() throws Exception {
        // Initialize the database
        photoRepository.saveAndFlush(photo);

		int databaseSizeBeforeDelete = photoRepository.findAll().size();

        // Get the photo
        restPhotoMockMvc.perform(delete("/api/photos/{id}", photo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Photo> photos = photoRepository.findAll();
        assertThat(photos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
