package com.epitech.pgt2019.web.rest;

import com.epitech.pgt2019.ExperienceServiceApp;

import com.epitech.pgt2019.domain.CountryExp;
import com.epitech.pgt2019.repository.CountryExpRepository;
import com.epitech.pgt2019.service.CountryExpService;
import com.epitech.pgt2019.service.dto.CountryExpDTO;
import com.epitech.pgt2019.service.mapper.CountryExpMapper;
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
 * Test class for the CountryExpResource REST controller.
 *
 * @see CountryExpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExperienceServiceApp.class)
public class CountryExpResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CountryExpRepository countryExpRepository;

    @Autowired
    private CountryExpMapper countryExpMapper;

    @Autowired
    private CountryExpService countryExpService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCountryExpMockMvc;

    private CountryExp countryExp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CountryExpResource countryExpResource = new CountryExpResource(countryExpService);
        this.restCountryExpMockMvc = MockMvcBuilders.standaloneSetup(countryExpResource)
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
    public static CountryExp createEntity() {
        CountryExp countryExp = new CountryExp()
            .name(DEFAULT_NAME);
        return countryExp;
    }

    @Before
    public void initTest() {
        countryExpRepository.deleteAll();
        countryExp = createEntity();
    }

    @Test
    public void createCountryExp() throws Exception {
        int databaseSizeBeforeCreate = countryExpRepository.findAll().size();

        // Create the CountryExp
        CountryExpDTO countryExpDTO = countryExpMapper.toDto(countryExp);
        restCountryExpMockMvc.perform(post("/api/country-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryExpDTO)))
            .andExpect(status().isCreated());

        // Validate the CountryExp in the database
        List<CountryExp> countryExpList = countryExpRepository.findAll();
        assertThat(countryExpList).hasSize(databaseSizeBeforeCreate + 1);
        CountryExp testCountryExp = countryExpList.get(countryExpList.size() - 1);
        assertThat(testCountryExp.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createCountryExpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryExpRepository.findAll().size();

        // Create the CountryExp with an existing ID
        countryExp.setId("existing_id");
        CountryExpDTO countryExpDTO = countryExpMapper.toDto(countryExp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryExpMockMvc.perform(post("/api/country-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryExpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryExp in the database
        List<CountryExp> countryExpList = countryExpRepository.findAll();
        assertThat(countryExpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllCountryExps() throws Exception {
        // Initialize the database
        countryExpRepository.save(countryExp);

        // Get all the countryExpList
        restCountryExpMockMvc.perform(get("/api/country-exps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryExp.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    public void getCountryExp() throws Exception {
        // Initialize the database
        countryExpRepository.save(countryExp);

        // Get the countryExp
        restCountryExpMockMvc.perform(get("/api/country-exps/{id}", countryExp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countryExp.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingCountryExp() throws Exception {
        // Get the countryExp
        restCountryExpMockMvc.perform(get("/api/country-exps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCountryExp() throws Exception {
        // Initialize the database
        countryExpRepository.save(countryExp);

        int databaseSizeBeforeUpdate = countryExpRepository.findAll().size();

        // Update the countryExp
        CountryExp updatedCountryExp = countryExpRepository.findById(countryExp.getId()).get();
        updatedCountryExp
            .name(UPDATED_NAME);
        CountryExpDTO countryExpDTO = countryExpMapper.toDto(updatedCountryExp);

        restCountryExpMockMvc.perform(put("/api/country-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryExpDTO)))
            .andExpect(status().isOk());

        // Validate the CountryExp in the database
        List<CountryExp> countryExpList = countryExpRepository.findAll();
        assertThat(countryExpList).hasSize(databaseSizeBeforeUpdate);
        CountryExp testCountryExp = countryExpList.get(countryExpList.size() - 1);
        assertThat(testCountryExp.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingCountryExp() throws Exception {
        int databaseSizeBeforeUpdate = countryExpRepository.findAll().size();

        // Create the CountryExp
        CountryExpDTO countryExpDTO = countryExpMapper.toDto(countryExp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryExpMockMvc.perform(put("/api/country-exps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryExpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CountryExp in the database
        List<CountryExp> countryExpList = countryExpRepository.findAll();
        assertThat(countryExpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCountryExp() throws Exception {
        // Initialize the database
        countryExpRepository.save(countryExp);

        int databaseSizeBeforeDelete = countryExpRepository.findAll().size();

        // Delete the countryExp
        restCountryExpMockMvc.perform(delete("/api/country-exps/{id}", countryExp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CountryExp> countryExpList = countryExpRepository.findAll();
        assertThat(countryExpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryExp.class);
        CountryExp countryExp1 = new CountryExp();
        countryExp1.setId("id1");
        CountryExp countryExp2 = new CountryExp();
        countryExp2.setId(countryExp1.getId());
        assertThat(countryExp1).isEqualTo(countryExp2);
        countryExp2.setId("id2");
        assertThat(countryExp1).isNotEqualTo(countryExp2);
        countryExp1.setId(null);
        assertThat(countryExp1).isNotEqualTo(countryExp2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryExpDTO.class);
        CountryExpDTO countryExpDTO1 = new CountryExpDTO();
        countryExpDTO1.setId("id1");
        CountryExpDTO countryExpDTO2 = new CountryExpDTO();
        assertThat(countryExpDTO1).isNotEqualTo(countryExpDTO2);
        countryExpDTO2.setId(countryExpDTO1.getId());
        assertThat(countryExpDTO1).isEqualTo(countryExpDTO2);
        countryExpDTO2.setId("id2");
        assertThat(countryExpDTO1).isNotEqualTo(countryExpDTO2);
        countryExpDTO1.setId(null);
        assertThat(countryExpDTO1).isNotEqualTo(countryExpDTO2);
    }
}
