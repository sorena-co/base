package ir.sp.base.service;

import com.querydsl.core.types.dsl.PathBuilder;
import ir.sp.base.domain.Course;
import ir.sp.base.domain.Program;
import ir.sp.base.repository.ClassGroupRepository;
import ir.sp.base.repository.CourseRepository;
import ir.sp.base.repository.ProgramRepository;
import ir.sp.base.repository.dsl.PredicatesBuilder;
import ir.sp.base.service.dto.ClassGroupDTO;
import ir.sp.base.service.dto.CourseDTO;
import ir.sp.base.service.dto.ProgramDTO;
import ir.sp.base.service.mapper.ClassGroupMapper;
import ir.sp.base.service.mapper.CourseMapper;
import ir.sp.base.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramService.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper classGroupMapper;

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public ProgramService(ProgramRepository programRepository, ProgramMapper programMapper, ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper, CourseRepository courseRepository, CourseMapper courseMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    public ProgramDTO save(ProgramDTO programDTO) {
        log.debug("Request to save Program : {}", programDTO);
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        return programMapper.toDto(program);
    }

    /**
     * Get all the programs.
     *
     * @param query
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(String query, Pageable pageable) {
        log.debug("Request to get all Programs");
        Page<Program> result;
        if (query != null) {
            result = programRepository.findAll(new PredicatesBuilder().build(query, new PathBuilder<>(Program.class, "vehicleModel"), null), pageable);
        } else {
            result = programRepository.findAll(pageable);
        }
        return result.map(programMapper::toDto);
    }

    /**
     * Get one program by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProgramDTO findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        Program program = programRepository.findOneWithEagerRelationships(id);
        return programMapper.toDto(program);
    }

    /**
     * Delete the program by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.delete(id);
    }

    public Page<ClassGroupDTO> findAllClassGroups(Long id, Pageable pageable) {
        return classGroupRepository.findAllByProgram_Id(id, pageable)
            .map(classGroupMapper::toDto);
    }

    public List<CourseDTO> findAllCourses(Long programId) {
        List<Course> courses = courseRepository.findAllByProgramId(programId);
        return courseMapper.toDto(courses);
    }
}
