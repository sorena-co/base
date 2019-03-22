package ir.sp.base.web.rest;

import ir.sp.base.BaseApp;

import ir.sp.base.config.SecurityBeanOverrideConfiguration;

import ir.sp.base.domain.InstitutionPerson;
import ir.sp.base.repository.InstitutionPersonRepository;
import ir.sp.base.service.InstitutionPersonService;
import ir.sp.base.service.dto.InstitutionPersonDTO;
import ir.sp.base.service.mapper.InstitutionPersonMapper;
import ir.sp.base.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static ir.sp.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InstitutionPersonResource REST controller.
 *
 * @see InstitutionPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseApp.class, SecurityBeanOverrideConfiguration.class})
public class InstitutionPersonResourceIntTest {

    @Autowired
    private InstitutionPersonRepository institutionPersonRepository;

    @Autowired
    private InstitutionPersonMapper institutionPersonMapper;

    @Autowired
    private InstitutionPersonService institutionPersonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInstitutionPersonMockMvc;

    private InstitutionPerson institutionPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstitutionPersonResource institutionPersonResource = new InstitutionPersonResource(institutionPersonService);
        this.restInstitutionPersonMockMvc = MockMvcBuilders.standaloneSetup(institutionPersonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstitutionPerson createEntity(EntityManager em) {
        InstitutionPerson institutionPerson = new InstitutionPerson();
        return institutionPerson;
    }

    @Before
    public void initTest() {
        institutionPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitutionPerson() throws Exception {
        int databaseSizeBeforeCreate = institutionPersonRepository.findAll().size();

        // Create the InstitutionPerson
        InstitutionPersonDTO institutionPersonDTO = institutionPersonMapper.toDto(institutionPerson);
        restInstitutionPersonMockMvc.perform(post("/api/institution-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institutionPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the InstitutionPerson in the database
        List<InstitutionPerson> institutionPersonList = institutionPersonRepository.findAll();
        assertThat(institutionPersonList).hasSize(databaseSizeBeforeCreate + 1);
        InstitutionPerson testInstitutionPerson = institutionPersonList.get(institutionPersonList.size() - 1);
    }

    @Test
    @Transactional
    public void createInstitutionPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = institutionPersonRepository.findAll().size();

        // Create the InstitutionPerson with an existing ID
        institutionPerson.setId(1L);
        InstitutionPersonDTO institutionPersonDTO = institutionPersonMapper.toDto(institutionPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitutionPersonMockMvc.perform(post("/api/institution-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institutionPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InstitutionPerson in the database
        List<InstitutionPerson> institutionPersonList = institutionPersonRepository.findAll();
        assertThat(institutionPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInstitutionPeople() throws Exception {
        // Initialize the database
        institutionPersonRepository.saveAndFlush(institutionPerson);

        // Get all the institutionPersonList
        restInstitutionPersonMockMvc.perform(get("/api/institution-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institutionPerson.getId().intValue())));
    }

    @Test
    @Transactional
    public void getInstitutionPerson() throws Exception {
        // Initialize the database
        institutionPersonRepository.saveAndFlush(institutionPerson);

        // Get the institutionPerson
        restInstitutionPersonMockMvc.perform(get("/api/institution-people/{id}", institutionPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institutionPerson.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitutionPerson() throws Exception {
        // Get the institutionPerson
        restInstitutionPersonMockMvc.perform(get("/api/institution-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitutionPerson() throws Exception {
        // Initialize the database
        institutionPersonRepository.saveAndFlush(institutionPerson);
        int databaseSizeBeforeUpdate = institutionPersonRepository.findAll().size();

        // Update the institutionPerson
        InstitutionPerson updatedInstitutionPerson = institutionPersonRepository.findOne(institutionPerson.getId());
        // Disconnect from session so that the updates on updatedInstitutionPerson are not directly saved in db
        em.detach(updatedInstitutionPerson);
        InstitutionPersonDTO institutionPersonDTO = institutionPersonMapper.toDto(updatedInstitutionPerson);

        restInstitutionPersonMockMvc.perform(put("/api/institution-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institutionPersonDTO)))
            .andExpect(status().isOk());

        // Validate the InstitutionPerson in the database
        List<InstitutionPerson> institutionPersonList = institutionPersonRepository.findAll();
        assertThat(institutionPersonList).hasSize(databaseSizeBeforeUpdate);
        InstitutionPerson testInstitutionPerson = institutionPersonList.get(institutionPersonList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitutionPerson() throws Exception {
        int databaseSizeBeforeUpdate = institutionPersonRepository.findAll().size();

        // Create the InstitutionPerson
        InstitutionPersonDTO institutionPersonDTO = institutionPersonMapper.toDto(institutionPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInstitutionPersonMockMvc.perform(put("/api/institution-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institutionPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the InstitutionPerson in the database
        List<InstitutionPerson> institutionPersonList = institutionPersonRepository.findAll();
        assertThat(institutionPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInstitutionPerson() throws Exception {
        // Initialize the database
        institutionPersonRepository.saveAndFlush(institutionPerson);
        int databaseSizeBeforeDelete = institutionPersonRepository.findAll().size();

        // Get the institutionPerson
        restInstitutionPersonMockMvc.perform(delete("/api/institution-people/{id}", institutionPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InstitutionPerson> institutionPersonList = institutionPersonRepository.findAll();
        assertThat(institutionPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstitutionPerson.class);
        InstitutionPerson institutionPerson1 = new InstitutionPerson();
        institutionPerson1.setId(1L);
        InstitutionPerson institutionPerson2 = new InstitutionPerson();
        institutionPerson2.setId(institutionPerson1.getId());
        assertThat(institutionPerson1).isEqualTo(institutionPerson2);
        institutionPerson2.setId(2L);
        assertThat(institutionPerson1).isNotEqualTo(institutionPerson2);
        institutionPerson1.setId(null);
        assertThat(institutionPerson1).isNotEqualTo(institutionPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstitutionPersonDTO.class);
        InstitutionPersonDTO institutionPersonDTO1 = new InstitutionPersonDTO();
        institutionPersonDTO1.setId(1L);
        InstitutionPersonDTO institutionPersonDTO2 = new InstitutionPersonDTO();
        assertThat(institutionPersonDTO1).isNotEqualTo(institutionPersonDTO2);
        institutionPersonDTO2.setId(institutionPersonDTO1.getId());
        assertThat(institutionPersonDTO1).isEqualTo(institutionPersonDTO2);
        institutionPersonDTO2.setId(2L);
        assertThat(institutionPersonDTO1).isNotEqualTo(institutionPersonDTO2);
        institutionPersonDTO1.setId(null);
        assertThat(institutionPersonDTO1).isNotEqualTo(institutionPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(institutionPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(institutionPersonMapper.fromId(null)).isNull();
    }
}
