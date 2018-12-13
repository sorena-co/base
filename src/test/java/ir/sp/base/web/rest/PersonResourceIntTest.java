package ir.sp.base.web.rest;

import ir.sp.base.BaseApp;

import ir.sp.base.config.SecurityBeanOverrideConfiguration;

import ir.sp.base.domain.Person;
import ir.sp.base.repository.PersonRepository;
import ir.sp.base.service.PersonService;
import ir.sp.base.service.dto.PersonDTO;
import ir.sp.base.service.mapper.PersonMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ir.sp.base.web.rest.TestUtil.sameInstant;
import static ir.sp.base.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ir.sp.base.domain.enumeration.Gender;
import ir.sp.base.domain.enumeration.Degree;
import ir.sp.base.domain.enumeration.PersonType;
/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseApp.class, SecurityBeanOverrideConfiguration.class})
public class PersonResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.WOMAN;

    private static final Degree DEFAULT_DEGREE = Degree.ASSOCIATE;
    private static final Degree UPDATED_DEGREE = Degree.BACHELORS;

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_CERTIFICATE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_CREDITS = 1;
    private static final Integer UPDATED_MAX_CREDITS = 2;

    private static final Float DEFAULT_PRIORITY = 1F;
    private static final Float UPDATED_PRIORITY = 2F;

    private static final PersonType DEFAULT_PERSON_TYPE = PersonType.PROF;
    private static final PersonType UPDATED_PERSON_TYPE = PersonType.PROF;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonService personService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
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
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fatherName(DEFAULT_FATHER_NAME)
            .gender(DEFAULT_GENDER)
            .degree(DEFAULT_DEGREE)
            .major(DEFAULT_MAJOR)
            .nationalId(DEFAULT_NATIONAL_ID)
            .birthCertificate(DEFAULT_BIRTH_CERTIFICATE)
            .birthDate(DEFAULT_BIRTH_DATE)
            .email(DEFAULT_EMAIL)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .postalCode(DEFAULT_POSTAL_CODE)
            .address(DEFAULT_ADDRESS)
            .maxCredits(DEFAULT_MAX_CREDITS)
            .priority(DEFAULT_PRIORITY)
            .personType(DEFAULT_PERSON_TYPE);
        return person;
    }

    @Before
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPerson.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testPerson.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPerson.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testPerson.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testPerson.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPerson.getBirthCertificate()).isEqualTo(DEFAULT_BIRTH_CERTIFICATE);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPerson.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testPerson.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPerson.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPerson.getMaxCredits()).isEqualTo(DEFAULT_MAX_CREDITS);
        assertThat(testPerson.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPerson.getPersonType()).isEqualTo(DEFAULT_PERSON_TYPE);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
            .andExpect(jsonPath("$.[*].birthCertificate").value(hasItem(DEFAULT_BIRTH_CERTIFICATE.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].maxCredits").value(hasItem(DEFAULT_MAX_CREDITS)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.doubleValue())))
            .andExpect(jsonPath("$.[*].personType").value(hasItem(DEFAULT_PERSON_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.birthCertificate").value(DEFAULT_BIRTH_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.maxCredits").value(DEFAULT_MAX_CREDITS))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.doubleValue()))
            .andExpect(jsonPath("$.personType").value(DEFAULT_PERSON_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fatherName(UPDATED_FATHER_NAME)
            .gender(UPDATED_GENDER)
            .degree(UPDATED_DEGREE)
            .major(UPDATED_MAJOR)
            .nationalId(UPDATED_NATIONAL_ID)
            .birthCertificate(UPDATED_BIRTH_CERTIFICATE)
            .birthDate(UPDATED_BIRTH_DATE)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .postalCode(UPDATED_POSTAL_CODE)
            .address(UPDATED_ADDRESS)
            .maxCredits(UPDATED_MAX_CREDITS)
            .priority(UPDATED_PRIORITY)
            .personType(UPDATED_PERSON_TYPE);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPerson.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPerson.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testPerson.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPerson.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testPerson.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPerson.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPerson.getBirthCertificate()).isEqualTo(UPDATED_BIRTH_CERTIFICATE);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPerson.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testPerson.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPerson.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPerson.getMaxCredits()).isEqualTo(UPDATED_MAX_CREDITS);
        assertThat(testPerson.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPerson.getPersonType()).isEqualTo(UPDATED_PERSON_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonDTO.class);
        PersonDTO personDTO1 = new PersonDTO();
        personDTO1.setId(1L);
        PersonDTO personDTO2 = new PersonDTO();
        assertThat(personDTO1).isNotEqualTo(personDTO2);
        personDTO2.setId(personDTO1.getId());
        assertThat(personDTO1).isEqualTo(personDTO2);
        personDTO2.setId(2L);
        assertThat(personDTO1).isNotEqualTo(personDTO2);
        personDTO1.setId(null);
        assertThat(personDTO1).isNotEqualTo(personDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personMapper.fromId(null)).isNull();
    }
}
