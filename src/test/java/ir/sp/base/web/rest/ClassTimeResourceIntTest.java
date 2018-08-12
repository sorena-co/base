package ir.sp.base.web.rest;

import ir.sp.base.BaseApp;

import ir.sp.base.config.SecurityBeanOverrideConfiguration;

import ir.sp.base.domain.ClassTime;
import ir.sp.base.repository.ClassTimeRepository;
import ir.sp.base.service.ClassTimeService;
import ir.sp.base.service.dto.ClassTimeDTO;
import ir.sp.base.service.mapper.ClassTimeMapper;
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

import ir.sp.base.domain.enumeration.Day;
/**
 * Test class for the ClassTimeResource REST controller.
 *
 * @see ClassTimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseApp.class, SecurityBeanOverrideConfiguration.class})
public class ClassTimeResourceIntTest {

    private static final Day DEFAULT_DAY = Day.SATURDAY;
    private static final Day UPDATED_DAY = Day.SUNDAY;

    private static final Float DEFAULT_START_TIME = 1F;
    private static final Float UPDATED_START_TIME = 2F;

    private static final Float DEFAULT_END_TIME = 1F;
    private static final Float UPDATED_END_TIME = 2F;

    private static final Float DEFAULT_PRIORITY = 1F;
    private static final Float UPDATED_PRIORITY = 2F;

    @Autowired
    private ClassTimeRepository classTimeRepository;

    @Autowired
    private ClassTimeMapper classTimeMapper;

    @Autowired
    private ClassTimeService classTimeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassTimeMockMvc;

    private ClassTime classTime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassTimeResource classTimeResource = new ClassTimeResource(classTimeService);
        this.restClassTimeMockMvc = MockMvcBuilders.standaloneSetup(classTimeResource)
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
    public static ClassTime createEntity(EntityManager em) {
        ClassTime classTime = new ClassTime()
            .day(DEFAULT_DAY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .priority(DEFAULT_PRIORITY);
        return classTime;
    }

    @Before
    public void initTest() {
        classTime = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassTime() throws Exception {
        int databaseSizeBeforeCreate = classTimeRepository.findAll().size();

        // Create the ClassTime
        ClassTimeDTO classTimeDTO = classTimeMapper.toDto(classTime);
        restClassTimeMockMvc.perform(post("/api/class-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassTime in the database
        List<ClassTime> classTimeList = classTimeRepository.findAll();
        assertThat(classTimeList).hasSize(databaseSizeBeforeCreate + 1);
        ClassTime testClassTime = classTimeList.get(classTimeList.size() - 1);
        assertThat(testClassTime.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testClassTime.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testClassTime.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testClassTime.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    public void createClassTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classTimeRepository.findAll().size();

        // Create the ClassTime with an existing ID
        classTime.setId(1L);
        ClassTimeDTO classTimeDTO = classTimeMapper.toDto(classTime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassTimeMockMvc.perform(post("/api/class-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTime in the database
        List<ClassTime> classTimeList = classTimeRepository.findAll();
        assertThat(classTimeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassTimes() throws Exception {
        // Initialize the database
        classTimeRepository.saveAndFlush(classTime);

        // Get all the classTimeList
        restClassTimeMockMvc.perform(get("/api/class-times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.doubleValue())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getClassTime() throws Exception {
        // Initialize the database
        classTimeRepository.saveAndFlush(classTime);

        // Get the classTime
        restClassTimeMockMvc.perform(get("/api/class-times/{id}", classTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classTime.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.doubleValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.doubleValue()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClassTime() throws Exception {
        // Get the classTime
        restClassTimeMockMvc.perform(get("/api/class-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassTime() throws Exception {
        // Initialize the database
        classTimeRepository.saveAndFlush(classTime);
        int databaseSizeBeforeUpdate = classTimeRepository.findAll().size();

        // Update the classTime
        ClassTime updatedClassTime = classTimeRepository.findOne(classTime.getId());
        // Disconnect from session so that the updates on updatedClassTime are not directly saved in db
        em.detach(updatedClassTime);
        updatedClassTime
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .priority(UPDATED_PRIORITY);
        ClassTimeDTO classTimeDTO = classTimeMapper.toDto(updatedClassTime);

        restClassTimeMockMvc.perform(put("/api/class-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classTimeDTO)))
            .andExpect(status().isOk());

        // Validate the ClassTime in the database
        List<ClassTime> classTimeList = classTimeRepository.findAll();
        assertThat(classTimeList).hasSize(databaseSizeBeforeUpdate);
        ClassTime testClassTime = classTimeList.get(classTimeList.size() - 1);
        assertThat(testClassTime.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testClassTime.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testClassTime.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testClassTime.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingClassTime() throws Exception {
        int databaseSizeBeforeUpdate = classTimeRepository.findAll().size();

        // Create the ClassTime
        ClassTimeDTO classTimeDTO = classTimeMapper.toDto(classTime);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassTimeMockMvc.perform(put("/api/class-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassTime in the database
        List<ClassTime> classTimeList = classTimeRepository.findAll();
        assertThat(classTimeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassTime() throws Exception {
        // Initialize the database
        classTimeRepository.saveAndFlush(classTime);
        int databaseSizeBeforeDelete = classTimeRepository.findAll().size();

        // Get the classTime
        restClassTimeMockMvc.perform(delete("/api/class-times/{id}", classTime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassTime> classTimeList = classTimeRepository.findAll();
        assertThat(classTimeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTime.class);
        ClassTime classTime1 = new ClassTime();
        classTime1.setId(1L);
        ClassTime classTime2 = new ClassTime();
        classTime2.setId(classTime1.getId());
        assertThat(classTime1).isEqualTo(classTime2);
        classTime2.setId(2L);
        assertThat(classTime1).isNotEqualTo(classTime2);
        classTime1.setId(null);
        assertThat(classTime1).isNotEqualTo(classTime2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTimeDTO.class);
        ClassTimeDTO classTimeDTO1 = new ClassTimeDTO();
        classTimeDTO1.setId(1L);
        ClassTimeDTO classTimeDTO2 = new ClassTimeDTO();
        assertThat(classTimeDTO1).isNotEqualTo(classTimeDTO2);
        classTimeDTO2.setId(classTimeDTO1.getId());
        assertThat(classTimeDTO1).isEqualTo(classTimeDTO2);
        classTimeDTO2.setId(2L);
        assertThat(classTimeDTO1).isNotEqualTo(classTimeDTO2);
        classTimeDTO1.setId(null);
        assertThat(classTimeDTO1).isNotEqualTo(classTimeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(classTimeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(classTimeMapper.fromId(null)).isNull();
    }
}
