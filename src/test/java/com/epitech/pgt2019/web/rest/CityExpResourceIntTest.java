package com.epitech.pgt2019.web.rest;

import com.epitech.pgt2019.ExperienceServiceApp;

import com.epitech.pgt2019.domain.CityExp;
import com.epitech.pgt2019.repository.CityExpRepository;
import com.epitech.pgt2019.service.CityExpService;
import com.epitech.pgt2019.service.dto.CityExpDTO;
import com.epitech.pgt2019.service.mapper.CityExpMapper;
import com.epitech.pgt2019.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;


import static com.epitech.pgt2019.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CityExpResource REST controller.
 *
 * @see CityExpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExperienceServiceApp.class)
public class CityExpResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CityExpRepository cityExpRepository;

    @Autowired
    private CityExpMapper cityExpMapper;

    @Autowired
    private CityExpService cityExpService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCityExpMockMvc;

    private CityExp cityExp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityExpResource cityExpResource = new CityExpResource(cityExpService);
        this.restCityExpMockMvc = MockMvcBuilders.standaloneSetup(cityExpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityExp createEntity() {
        CityExp cityExp = new CityExp()
            .name(DEFAULT_NAME);
        return cityExp;
    }

    @Before
    public void initTest() {
        cityExpRepository.deleteAll();
        cityExp = createEntity();
    }

    @Test
    public void createCityExp() throws Exception {
        int databaseSizeBeforeCreate = cityExpRepository.findAll().size();

        // Create the CityExp
        CityExpDTO cityExpDTO = cityExpMapper.toDto(cityExp);
        restCityExpMockMvc.perform(post("/api/city-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityExpDTO)))
            .andExpect(status().isCreated());

        // Validate the CityExp in the database
        List<CityExp> cityExpList = cityExpRepository.findAll();
        assertThat(cityExpList).hasSize(databaseSizeBeforeCreate + 1);
        CityExp testCityExp = cityExpList.get(cityExpList.size() - 1);
        assertThat(testCityExp.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createCityExpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityExpRepository.findAll().size();

        // Create the CityExp with an existing ID
        cityExp.setId("existing_id");
        CityExpDTO cityExpDTO = cityExpMapper.toDto(cityExp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityExpMockMvc.perform(post("/api/city-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityExpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityExp in the database
        List<CityExp> cityExpList = cityExpRepository.findAll();
        assertThat(cityExpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCityExps() throws Exception {
        // Initialize the database
        cityExpRepository.save(cityExp);

        // Get all the cityExpList
        restCityExpMockMvc.perform(get("/api/city-exps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityExp.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    public void getCityExp() throws Exception {
        // Initialize the database
        cityExpRepository.save(cityExp);

        // Get the cityExp
        restCityExpMockMvc.perform(get("/api/city-exps/{id}", cityExp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cityExp.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingCityExp() throws Exception {
        // Get the cityExp
        restCityExpMockMvc.perform(get("/api/city-exps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCityExp() throws Exception {
        // Initialize the database
        cityExpRepository.save(cityExp);

        int databaseSizeBeforeUpdate = cityExpRepository.findAll().size();

        // Update the cityExp
        CityExp updatedCityExp = cityExpRepository.findById(cityExp.getId()).get();
        updatedCityExp
            .name(UPDATED_NAME);
        CityExpDTO cityExpDTO = cityExpMapper.toDto(updatedCityExp);

        restCityExpMockMvc.perform(put("/api/city-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityExpDTO)))
            .andExpect(status().isOk());

        // Validate the CityExp in the database
        List<CityExp> cityExpList = cityExpRepository.findAll();
        assertThat(cityExpList).hasSize(databaseSizeBeforeUpdate);
        CityExp testCityExp = cityExpList.get(cityExpList.size() - 1);
        assertThat(testCityExp.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingCityExp() throws Exception {
        int databaseSizeBeforeUpdate = cityExpRepository.findAll().size();

        // Create the CityExp
        CityExpDTO cityExpDTO = cityExpMapper.toDto(cityExp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityExpMockMvc.perform(put("/api/city-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cityExpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityExp in the database
        List<CityExp> cityExpList = cityExpRepository.findAll();
        assertThat(cityExpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCityExp() throws Exception {
        // Initialize the database
        cityExpRepository.save(cityExp);

        int databaseSizeBeforeDelete = cityExpRepository.findAll().size();

        // Delete the cityExp
        restCityExpMockMvc.perform(delete("/api/city-exps/{id}", cityExp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CityExp> cityExpList = cityExpRepository.findAll();
        assertThat(cityExpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityExp.class);
        CityExp cityExp1 = new CityExp();
        cityExp1.setId("id1");
        CityExp cityExp2 = new CityExp();
        cityExp2.setId(cityExp1.getId());
        assertThat(cityExp1).isEqualTo(cityExp2);
        cityExp2.setId("id2");
        assertThat(cityExp1).isNotEqualTo(cityExp2);
        cityExp1.setId(null);
        assertThat(cityExp1).isNotEqualTo(cityExp2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityExpDTO.class);
        CityExpDTO cityExpDTO1 = new CityExpDTO();
        cityExpDTO1.setId("id1");
        CityExpDTO cityExpDTO2 = new CityExpDTO();
        assertThat(cityExpDTO1).isNotEqualTo(cityExpDTO2);
        cityExpDTO2.setId(cityExpDTO1.getId());
        assertThat(cityExpDTO1).isEqualTo(cityExpDTO2);
        cityExpDTO2.setId("id2");
        assertThat(cityExpDTO1).isNotEqualTo(cityExpDTO2);
        cityExpDTO1.setId(null);
        assertThat(cityExpDTO1).isNotEqualTo(cityExpDTO2);
    }
}
