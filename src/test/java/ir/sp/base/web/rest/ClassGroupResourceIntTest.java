package ir.sp.base.web.rest;

import ir.sp.base.BaseApp;

import ir.sp.base.config.SecurityBeanOverrideConfiguration;

import ir.sp.base.domain.ClassGroup;
import ir.sp.base.repository.ClassGroupRepository;
import ir.sp.base.service.ClassGroupService;
import ir.sp.base.service.dto.ClassGroupDTO;
import ir.sp.base.service.mapper.ClassGroupMapper;
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
 * Test class for the ClassGroupResource REST controller.
 *
 * @see ClassGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseApp.class, SecurityBeanOverrideConfiguration.class})
public class ClassGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    private ClassGroupMapper classGroupMapper;

    @Autowired
    private ClassGroupService classGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassGroupMockMvc;

    private ClassGroup classGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassGroupResource classGroupResource = new ClassGroupResource(classGroupService);
        this.restClassGroupMockMvc = MockMvcBuilders.standaloneSetup(classGroupResource)
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
    public static ClassGroup createEntity(EntityManager em) {
        ClassGroup classGroup = new ClassGroup()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return classGroup;
    }

    @Before
    public void initTest() {
        classGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassGroup() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassGroup.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createClassGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classGroupRepository.findAll().size();

        // Create the ClassGroup with an existing ID
        classGroup.setId(1L);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassGroupMockMvc.perform(post("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassGroups() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get all the classGroupList
        restClassGroupMockMvc.perform(get("/api/class-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);

        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", classGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassGroup() throws Exception {
        // Get the classGroup
        restClassGroupMockMvc.perform(get("/api/class-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);
        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Update the classGroup
        ClassGroup updatedClassGroup = classGroupRepository.findOne(classGroup.getId());
        // Disconnect from session so that the updates on updatedClassGroup are not directly saved in db
        em.detach(updatedClassGroup);
        updatedClassGroup
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(updatedClassGroup);

        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate);
        ClassGroup testClassGroup = classGroupList.get(classGroupList.size() - 1);
        assertThat(testClassGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassGroup.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassGroup() throws Exception {
        int databaseSizeBeforeUpdate = classGroupRepository.findAll().size();

        // Create the ClassGroup
        ClassGroupDTO classGroupDTO = classGroupMapper.toDto(classGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassGroupMockMvc.perform(put("/api/class-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassGroup in the database
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassGroup() throws Exception {
        // Initialize the database
        classGroupRepository.saveAndFlush(classGroup);
        int databaseSizeBeforeDelete = classGroupRepository.findAll().size();

        // Get the classGroup
        restClassGroupMockMvc.perform(delete("/api/class-groups/{id}", classGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassGroup> classGroupList = classGroupRepository.findAll();
        assertThat(classGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroup.class);
        ClassGroup classGroup1 = new ClassGroup();
        classGroup1.setId(1L);
        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setId(classGroup1.getId());
        assertThat(classGroup1).isEqualTo(classGroup2);
        classGroup2.setId(2L);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
        classGroup1.setId(null);
        assertThat(classGroup1).isNotEqualTo(classGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassGroupDTO.class);
        ClassGroupDTO classGroupDTO1 = new ClassGroupDTO();
        classGroupDTO1.setId(1L);
        ClassGroupDTO classGroupDTO2 = new ClassGroupDTO();
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO2.setId(classGroupDTO1.getId());
        assertThat(classGroupDTO1).isEqualTo(classGroupDTO2);
        classGroupDTO2.setId(2L);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
        classGroupDTO1.setId(null);
        assertThat(classGroupDTO1).isNotEqualTo(classGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classGroupMapper.fromId(null)).isNull();
    }
}
