package com.epitech.pgt2019.web.rest;

import com.epitech.pgt2019.ExperienceServiceApp;

import com.epitech.pgt2019.domain.Experience;
import com.epitech.pgt2019.repository.ExperienceRepository;
import com.epitech.pgt2019.service.ExperienceService;
import com.epitech.pgt2019.service.dto.ExperienceDTO;
import com.epitech.pgt2019.service.mapper.ExperienceMapper;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.epitech.pgt2019.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExperienceResource REST controller.
 *
 * @see ExperienceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExperienceServiceApp.class)
public class ExperienceResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BEGINNING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGINNING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDING_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restExperienceMockMvc;

    private Experience experience;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceResource experienceResource = new ExperienceResource(experienceService);
        this.restExperienceMockMvc = MockMvcBuilders.standaloneSetup(experienceResource)
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
    public static Experience createEntity() {
        Experience experience = new Experience()
            .title(DEFAULT_TITLE)
            .beginningDate(DEFAULT_BEGINNING_DATE)
            .endingDate(DEFAULT_ENDING_DATE);
        return experience;
    }

    @Before
    public void initTest() {
        experienceRepository.deleteAll();
        experience = createEntity();
    }

    @Test
    public void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExperience.getBeginningDate()).isEqualTo(DEFAULT_BEGINNING_DATE);
        assertThat(testExperience.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
    }

    @Test
    public void createExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience with an existing ID
        experience.setId("existing_id");
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setTitle(null);

        // Create the Experience, which fails.
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBeginningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setBeginningDate(null);

        // Create the Experience, which fails.
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.save(experience);

        // Get all the experienceList
        restExperienceMockMvc.perform(get("/api/experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].beginningDate").value(hasItem(DEFAULT_BEGINNING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())));
    }
    
    @Test
    public void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.save(experience);

        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.beginningDate").value(DEFAULT_BEGINNING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()));
    }

    @Test
    public void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateExperience() throws Exception {
        // Initialize the database
        experienceRepository.save(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).get();
        updatedExperience
            .title(UPDATED_TITLE)
            .beginningDate(UPDATED_BEGINNING_DATE)
            .endingDate(UPDATED_ENDING_DATE);
        ExperienceDTO experienceDTO = experienceMapper.toDto(updatedExperience);

        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExperience.getBeginningDate()).isEqualTo(UPDATED_BEGINNING_DATE);
        assertThat(testExperience.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
    }

    @Test
    public void updateNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteExperience() throws Exception {
        // Initialize the database
        experienceRepository.save(experience);

        int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Delete the experience
        restExperienceMockMvc.perform(delete("/api/experiences/{id}", experience.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Experience.class);
        Experience experience1 = new Experience();
        experience1.setId("id1");
        Experience experience2 = new Experience();
        experience2.setId(experience1.getId());
        assertThat(experience1).isEqualTo(experience2);
        experience2.setId("id2");
        assertThat(experience1).isNotEqualTo(experience2);
        experience1.setId(null);
        assertThat(experience1).isNotEqualTo(experience2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceDTO.class);
        ExperienceDTO experienceDTO1 = new ExperienceDTO();
        experienceDTO1.setId("id1");
        ExperienceDTO experienceDTO2 = new ExperienceDTO();
        assertThat(experienceDTO1).isNotEqualTo(experienceDTO2);
        experienceDTO2.setId(experienceDTO1.getId());
        assertThat(experienceDTO1).isEqualTo(experienceDTO2);
        experienceDTO2.setId("id2");
        assertThat(experienceDTO1).isNotEqualTo(experienceDTO2);
        experienceDTO1.setId(null);
        assertThat(experienceDTO1).isNotEqualTo(experienceDTO2);
    }
}
