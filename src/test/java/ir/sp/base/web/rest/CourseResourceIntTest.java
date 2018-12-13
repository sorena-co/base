package ir.sp.base.web.rest;

import ir.sp.base.BaseApp;

import ir.sp.base.config.SecurityBeanOverrideConfiguration;

import ir.sp.base.domain.Course;
import ir.sp.base.repository.CourseRepository;
import ir.sp.base.service.CourseService;
import ir.sp.base.service.dto.CourseDTO;
import ir.sp.base.service.mapper.CourseMapper;
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
 * Test class for the CourseResource REST controller.
 *
 * @see CourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseApp.class, SecurityBeanOverrideConfiguration.class})
public class CourseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEED_LAB = false;
    private static final Boolean UPDATED_NEED_LAB = true;

    private static final Boolean DEFAULT_NEED_PROJECTOR = false;
    private static final Boolean UPDATED_NEED_PROJECTOR = true;

    private static final Integer DEFAULT_PRACTICAL_CREDIT = 1;
    private static final Integer UPDATED_PRACTICAL_CREDIT = 2;

    private static final Integer DEFAULT_THEORETICAL_CREDIT = 1;
    private static final Integer UPDATED_THEORETICAL_CREDIT = 2;

    private static final Integer DEFAULT_PRACTICAL_HOURS = 1;
    private static final Integer UPDATED_PRACTICAL_HOURS = 2;

    private static final Integer DEFAULT_THEORETICAL_HOURS = 1;
    private static final Integer UPDATED_THEORETICAL_HOURS = 2;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourseMockMvc;

    private Course course;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseResource courseResource = new CourseResource(courseService);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
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
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .name(DEFAULT_NAME)
            .needLab(DEFAULT_NEED_LAB)
            .needProjector(DEFAULT_NEED_PROJECTOR)
            .practicalCredit(DEFAULT_PRACTICAL_CREDIT)
            .theoreticalCredit(DEFAULT_THEORETICAL_CREDIT)
            .practicalHours(DEFAULT_PRACTICAL_HOURS)
            .theoreticalHours(DEFAULT_THEORETICAL_HOURS);
        return course;
    }

    @Before
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCourse.isNeedLab()).isEqualTo(DEFAULT_NEED_LAB);
        assertThat(testCourse.isNeedProjector()).isEqualTo(DEFAULT_NEED_PROJECTOR);
        assertThat(testCourse.getPracticalCredit()).isEqualTo(DEFAULT_PRACTICAL_CREDIT);
        assertThat(testCourse.getTheoreticalCredit()).isEqualTo(DEFAULT_THEORETICAL_CREDIT);
        assertThat(testCourse.getPracticalHours()).isEqualTo(DEFAULT_PRACTICAL_HOURS);
        assertThat(testCourse.getTheoreticalHours()).isEqualTo(DEFAULT_THEORETICAL_HOURS);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        course.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(course);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].needLab").value(hasItem(DEFAULT_NEED_LAB.booleanValue())))
            .andExpect(jsonPath("$.[*].needProjector").value(hasItem(DEFAULT_NEED_PROJECTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].practicalCredit").value(hasItem(DEFAULT_PRACTICAL_CREDIT)))
            .andExpect(jsonPath("$.[*].theoreticalCredit").value(hasItem(DEFAULT_THEORETICAL_CREDIT)))
            .andExpect(jsonPath("$.[*].practicalHours").value(hasItem(DEFAULT_PRACTICAL_HOURS)))
            .andExpect(jsonPath("$.[*].theoreticalHours").value(hasItem(DEFAULT_THEORETICAL_HOURS)));
    }

    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.needLab").value(DEFAULT_NEED_LAB.booleanValue()))
            .andExpect(jsonPath("$.needProjector").value(DEFAULT_NEED_PROJECTOR.booleanValue()))
            .andExpect(jsonPath("$.practicalCredit").value(DEFAULT_PRACTICAL_CREDIT))
            .andExpect(jsonPath("$.theoreticalCredit").value(DEFAULT_THEORETICAL_CREDIT))
            .andExpect(jsonPath("$.practicalHours").value(DEFAULT_PRACTICAL_HOURS))
            .andExpect(jsonPath("$.theoreticalHours").value(DEFAULT_THEORETICAL_HOURS));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findOne(course.getId());
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .name(UPDATED_NAME)
            .needLab(UPDATED_NEED_LAB)
            .needProjector(UPDATED_NEED_PROJECTOR)
            .practicalCredit(UPDATED_PRACTICAL_CREDIT)
            .theoreticalCredit(UPDATED_THEORETICAL_CREDIT)
            .practicalHours(UPDATED_PRACTICAL_HOURS)
            .theoreticalHours(UPDATED_THEORETICAL_HOURS);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourse);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCourse.isNeedLab()).isEqualTo(UPDATED_NEED_LAB);
        assertThat(testCourse.isNeedProjector()).isEqualTo(UPDATED_NEED_PROJECTOR);
        assertThat(testCourse.getPracticalCredit()).isEqualTo(UPDATED_PRACTICAL_CREDIT);
        assertThat(testCourse.getTheoreticalCredit()).isEqualTo(UPDATED_THEORETICAL_CREDIT);
        assertThat(testCourse.getPracticalHours()).isEqualTo(UPDATED_PRACTICAL_HOURS);
        assertThat(testCourse.getTheoreticalHours()).isEqualTo(UPDATED_THEORETICAL_HOURS);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(course);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Get the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);
        course2.setId(2L);
        assertThat(course1).isNotEqualTo(course2);
        course1.setId(null);
        assertThat(course1).isNotEqualTo(course2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseDTO.class);
        CourseDTO courseDTO1 = new CourseDTO();
        courseDTO1.setId(1L);
        CourseDTO courseDTO2 = new CourseDTO();
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO2.setId(courseDTO1.getId());
        assertThat(courseDTO1).isEqualTo(courseDTO2);
        courseDTO2.setId(2L);
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
        courseDTO1.setId(null);
        assertThat(courseDTO1).isNotEqualTo(courseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(courseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(courseMapper.fromId(null)).isNull();
    }
}
