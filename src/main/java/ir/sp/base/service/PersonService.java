package ir.sp.base.service;

import ir.sp.base.domain.ClassTime;
import ir.sp.base.domain.InstitutionPerson;
import ir.sp.base.domain.Person;
import ir.sp.base.repository.ClassTimeRepository;
import ir.sp.base.repository.InstitutionPersonRepository;
import ir.sp.base.repository.InstitutionRepository;
import ir.sp.base.repository.PersonRepository;
import ir.sp.base.security.AuthoritiesConstants;
import ir.sp.base.service.dto.PersonDTO;
import ir.sp.base.service.dto.feign.UserDTO;
import ir.sp.base.service.feign.UaaFeignClient;
import ir.sp.base.service.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final ClassTimeRepository classTimeRepository;

    private final InstitutionPersonRepository institutionPersonRepository;

    private final InstitutionRepository institutionRepository;

    private final UaaFeignClient uaaFeignClient;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, ClassTimeRepository classTimeRepository, InstitutionPersonRepository institutionPersonRepository, InstitutionRepository institutionRepository, UaaFeignClient uaaFeignClient) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.classTimeRepository = classTimeRepository;
        this.institutionPersonRepository = institutionPersonRepository;
        this.institutionRepository = institutionRepository;
        this.uaaFeignClient = uaaFeignClient;
    }

    /**
     * Save a person.
     *
     * @param personDTO the entity to save
     * @return the persisted entity
     */
    public PersonDTO save(PersonDTO personDTO) {
        log.debug("Request to save Person : {}", personDTO);
        Person person = personMapper.toEntity(personDTO);
        Set<String> authorities = new HashSet<>();
        authorities.add(AuthoritiesConstants.TEACHER);
        UserDTO user = uaaFeignClient.getUser(personDTO.getEmail());

        if (personDTO.getId() == null) { // this request for save person.
            person = personRepository.save(person);
            InstitutionPerson institutionPerson = new InstitutionPerson();
            institutionPerson.setPerson(person);
            institutionPerson.setInstitution(institutionRepository.findOne(personDTO.getInstitutionId()));

            institutionPerson = institutionPersonRepository.save(institutionPerson);
            for (ClassTime preferenceTime : personDTO.getPreferenceTimes()) {
                preferenceTime.setInstitutionPerson(institutionPerson);
            }
            classTimeRepository.save(personDTO.getPreferenceTimes());
        } else {
            InstitutionPerson institutionPerson = institutionPersonRepository.findFirstByInstitution_IdAndPerson_Id(personDTO.getInstitutionId(), personDTO.getId());
            classTimeRepository.deleteAllByInstitutionPerson_Id(institutionPerson.getId());

            for (ClassTime preferenceTime : personDTO.getPreferenceTimes()) {
                preferenceTime.setInstitutionPerson(institutionPerson);
            }
            classTimeRepository.save(personDTO.getPreferenceTimes());
        }

        if (user == null || user.getId() == null) {
            user = new UserDTO();
            user.setEmail(personDTO.getEmail());
            user.setLogin(personDTO.getEmail());
            user.setAuthorities(authorities);
            user.setPassword(personDTO.getNationalId());
            user.setPersonId(person.getId());

            user = uaaFeignClient.register(user);
        }


//        if (personDTO.getId() != null)
//            classTimeRepository.deleteAllByPerson_Id(personDTO.getId());

        /*person = personRepository.save(person);
        Set<ClassTime> preferenceTimes = personDTO.getPreferenceTimes();
        Person finalPerson = person;
        preferenceTimes.forEach(classTime -> {
            classTime.setId(null);
            classTime.setPerson(finalPerson);
//        });*/
//        classTimeRepository.save(preferenceTimes);
        return personMapper.toDto(person);
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return personRepository.findAll(pageable)
            .map(personMapper::toDto);
    }

    /**
     * Get one person by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public PersonDTO findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        Person person = personRepository.findOne(id);
        return personMapper.toDto(person);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
//        institutionPersonRepository.findFirstByInstitution_IdAndPerson_Id()
        personRepository.delete(id);
    }
}
