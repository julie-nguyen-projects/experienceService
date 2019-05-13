package com.epitech.pgt2019.web.rest;

import com.epitech.pgt2019.ExperienceServiceApp;

import com.epitech.pgt2019.domain.ExpUser;
import com.epitech.pgt2019.repository.ExpUserRepository;
import com.epitech.pgt2019.service.ExpUserService;
import com.epitech.pgt2019.service.dto.ExpUserDTO;
import com.epitech.pgt2019.service.mapper.ExpUserMapper;
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
 * Test class for the ExpUserResource REST controller.
 *
 * @see ExpUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExperienceServiceApp.class)
public class ExpUserResourceIntTest {

    @Autowired
    private ExpUserRepository expUserRepository;

    @Autowired
    private ExpUserMapper expUserMapper;

    @Autowired
    private ExpUserService expUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restExpUserMockMvc;

    private ExpUser expUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpUserResource expUserResource = new ExpUserResource(expUserService);
        this.restExpUserMockMvc = MockMvcBuilders.standaloneSetup(expUserResource)
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
    public static ExpUser createEntity() {
        ExpUser expUser = new ExpUser();
        return expUser;
    }

    @Before
    public void initTest() {
        expUserRepository.deleteAll();
        expUser = createEntity();
    }

    @Test
    public void createExpUser() throws Exception {
        int databaseSizeBeforeCreate = expUserRepository.findAll().size();

        // Create the ExpUser
        ExpUserDTO expUserDTO = expUserMapper.toDto(expUser);
        restExpUserMockMvc.perform(post("/api/exp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ExpUser in the database
        List<ExpUser> expUserList = expUserRepository.findAll();
        assertThat(expUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExpUser testExpUser = expUserList.get(expUserList.size() - 1);
    }

    @Test
    public void createExpUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expUserRepository.findAll().size();

        // Create the ExpUser with an existing ID
        expUser.setId("existing_id");
        ExpUserDTO expUserDTO = expUserMapper.toDto(expUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpUserMockMvc.perform(post("/api/exp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpUser in the database
        List<ExpUser> expUserList = expUserRepository.findAll();
        assertThat(expUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllExpUsers() throws Exception {
        // Initialize the database
        expUserRepository.save(expUser);

        // Get all the expUserList
        restExpUserMockMvc.perform(get("/api/exp-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expUser.getId())));
    }
    
    @Test
    public void getExpUser() throws Exception {
        // Initialize the database
        expUserRepository.save(expUser);

        // Get the expUser
        restExpUserMockMvc.perform(get("/api/exp-users/{id}", expUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expUser.getId()));
    }

    @Test
    public void getNonExistingExpUser() throws Exception {
        // Get the expUser
        restExpUserMockMvc.perform(get("/api/exp-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateExpUser() throws Exception {
        // Initialize the database
        expUserRepository.save(expUser);

        int databaseSizeBeforeUpdate = expUserRepository.findAll().size();

        // Update the expUser
        ExpUser updatedExpUser = expUserRepository.findById(expUser.getId()).get();
        ExpUserDTO expUserDTO = expUserMapper.toDto(updatedExpUser);

        restExpUserMockMvc.perform(put("/api/exp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expUserDTO)))
            .andExpect(status().isOk());

        // Validate the ExpUser in the database
        List<ExpUser> expUserList = expUserRepository.findAll();
        assertThat(expUserList).hasSize(databaseSizeBeforeUpdate);
        ExpUser testExpUser = expUserList.get(expUserList.size() - 1);
    }

    @Test
    public void updateNonExistingExpUser() throws Exception {
        int databaseSizeBeforeUpdate = expUserRepository.findAll().size();

        // Create the ExpUser
        ExpUserDTO expUserDTO = expUserMapper.toDto(expUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpUserMockMvc.perform(put("/api/exp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExpUser in the database
        List<ExpUser> expUserList = expUserRepository.findAll();
        assertThat(expUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteExpUser() throws Exception {
        // Initialize the database
        expUserRepository.save(expUser);

        int databaseSizeBeforeDelete = expUserRepository.findAll().size();

        // Delete the expUser
        restExpUserMockMvc.perform(delete("/api/exp-users/{id}", expUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExpUser> expUserList = expUserRepository.findAll();
        assertThat(expUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpUser.class);
        ExpUser expUser1 = new ExpUser();
        expUser1.setId("id1");
        ExpUser expUser2 = new ExpUser();
        expUser2.setId(expUser1.getId());
        assertThat(expUser1).isEqualTo(expUser2);
        expUser2.setId("id2");
        assertThat(expUser1).isNotEqualTo(expUser2);
        expUser1.setId(null);
        assertThat(expUser1).isNotEqualTo(expUser2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpUserDTO.class);
        ExpUserDTO expUserDTO1 = new ExpUserDTO();
        expUserDTO1.setId("id1");
        ExpUserDTO expUserDTO2 = new ExpUserDTO();
        assertThat(expUserDTO1).isNotEqualTo(expUserDTO2);
        expUserDTO2.setId(expUserDTO1.getId());
        assertThat(expUserDTO1).isEqualTo(expUserDTO2);
        expUserDTO2.setId("id2");
        assertThat(expUserDTO1).isNotEqualTo(expUserDTO2);
        expUserDTO1.setId(null);
        assertThat(expUserDTO1).isNotEqualTo(expUserDTO2);
    }
}
