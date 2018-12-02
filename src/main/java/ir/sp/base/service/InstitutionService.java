package ir.sp.base.service;

import ir.sp.base.domain.*;
import ir.sp.base.repository.*;
import ir.sp.base.service.dto.*;
import ir.sp.base.service.dto.custom.FeignPlanDTO;
import ir.sp.base.service.dto.feign.GaModel;
import ir.sp.base.service.feign.AiFeignClient;
import ir.sp.base.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Institution.
 */
@Service
@Transactional
public class InstitutionService {

    private final Logger log = LoggerFactory.getLogger(InstitutionService.class);

    private final InstitutionRepository institutionRepository;

    private final InstitutionMapper institutionMapper;

    private final AiFeignClient aiFeignClient;

    private final ClassRoomRepository classRoomRepository;

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    private final ClassGroupRepository classGroupRepository;

    public InstitutionService(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper, AiFeignClient aiFeignClient, PersonRepository personRepository, RoomRepository roomRepository, ClassRoomRepository classRoomRepository, CourseRepository courseRepository, CourseMapper courseMapper, ProgramRepository programRepository, ProgramMapper programMapper, RoomMapper roomMapper, PersonMapper personMapper, SemesterRepository semesterRepository, SemesterMapper semesterMapper, ClassGroupRepository classGroupRepository) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
        this.aiFeignClient = aiFeignClient;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
        this.classRoomRepository = classRoomRepository;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.programRepository = programRepository;
        this.programMapper = programMapper;
        this.roomMapper = roomMapper;
        this.personMapper = personMapper;
        this.semesterRepository = semesterRepository;
        this.semesterMapper = semesterMapper;
        this.classGroupRepository = classGroupRepository;
    }

    /**
     * Save a institution.
     *
     * @param institutionDTO the entity to save
     * @return the persisted entity
     */
    public InstitutionDTO save(InstitutionDTO institutionDTO) {
        log.debug("Request to save Institution : {}", institutionDTO);
        Institution institution = institutionMapper.toEntity(institutionDTO);
        institution = institutionRepository.save(institution);
        return institutionMapper.toDto(institution);
    }

    /**
     * Get all the institutions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InstitutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Institutions");
        return institutionRepository.findAll(pageable)
            .map(institutionMapper::toDto);
    }

    /**
     * Get one institution by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InstitutionDTO findOne(Long id) {
        log.debug("Request to get Institution : {}", id);
        Institution institution = institutionRepository.findOne(id);
        return institutionMapper.toDto(institution);
    }

    /**
     * Delete the institution by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Institution : {}", id);
        institutionRepository.delete(id);
    }

    public String startPlaning(Long id) {
        List<Person> profs = personRepository.findAllByInstitution_Id(id);
        List<Room> rooms = roomRepository.findAllByInstitution_Id(id);
        List<ClassRoom> classRooms = classRoomRepository.findAllClassRoomByInstitutionId(id);
        List<Course> courses = courseRepository.findAllByInstitution_Id(id);

        FeignPlanDTO feignPlanDTO = institutionMapper.toFeignPlanDTO(id, profs, rooms, courses, classRooms);
        String s = aiFeignClient.startPlaning(feignPlanDTO);
//        PlanDTO planDTO = institutionMapper.toPlanDTO(id, profs, rooms, courses, classRooms);
//        String s = aiFeignClient.startPlaning(planDTO);
        return s;
    }

    public Page<ProgramDTO> findAllPrograms(Long institutionId, Pageable pageable) {
        return programRepository.findAllByInstitution_Id(institutionId, pageable)
            .map(programMapper::toDto);
    }

    public Page<PersonDTO> findAllPersons(Long institutionId, Pageable pageable) {
        return personRepository.findAllByInstitution_Id(institutionId, pageable)
            .map(personMapper::toDto);
    }

    public Page<SemesterDTO> findAllSemester(Long institutionId, Pageable pageable) {
        return semesterRepository.findAllByInstitution_Id(institutionId, pageable)
            .map(semesterMapper::toDto);
    }

    public Page<RoomDTO> findAllRoom(Long institutionId, Pageable pageable) {
        return roomRepository.findAllByInstitution_Id(institutionId, pageable)
            .map(roomMapper::toDto);
    }

    public Page<CourseDTO> findAllCourses(Long institutionId, Pageable pageable) {
        return courseRepository.findAllByInstitution_Id(institutionId, pageable)
            .map(courseMapper::toDto);
    }

    public List<CourseDTO> findAllCourses(Long institutionId) {
        return courseMapper.toDto(courseRepository.findAllByInstitution_Id(institutionId));
    }

    public GaModel getPlan(Long id) {
        return aiFeignClient.getPlaning(id);
    }
}
