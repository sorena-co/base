package ir.sp.base.service;

import ir.sp.base.domain.ClassTime;
import ir.sp.base.repository.ClassTimeRepository;
import ir.sp.base.service.dto.ClassTimeDTO;
import ir.sp.base.service.mapper.ClassTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassTime.
 */
@Service
@Transactional
public class ClassTimeService {

    private final Logger log = LoggerFactory.getLogger(ClassTimeService.class);

    private final ClassTimeRepository classTimeRepository;

    private final ClassTimeMapper classTimeMapper;

    public ClassTimeService(ClassTimeRepository classTimeRepository, ClassTimeMapper classTimeMapper) {
        this.classTimeRepository = classTimeRepository;
        this.classTimeMapper = classTimeMapper;
    }

    /**
     * Save a classTime.
     *
     * @param classTimeDTO the entity to save
     * @return the persisted entity
     */
    public ClassTimeDTO save(ClassTimeDTO classTimeDTO) {
        log.debug("Request to save ClassTime : {}", classTimeDTO);
        ClassTime classTime = classTimeMapper.toEntity(classTimeDTO);
        classTime = classTimeRepository.save(classTime);
        return classTimeMapper.toDto(classTime);
    }

    /**
     * Get all the classTimes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassTimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassTimes");
        return classTimeRepository.findAll(pageable)
            .map(classTimeMapper::toDto);
    }

    /**
     * Get one classTime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClassTimeDTO findOne(Long id) {
        log.debug("Request to get ClassTime : {}", id);
        ClassTime classTime = classTimeRepository.findOne(id);
        return classTimeMapper.toDto(classTime);
    }

    /**
     * Delete the classTime by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassTime : {}", id);
        classTimeRepository.delete(id);
    }
}
