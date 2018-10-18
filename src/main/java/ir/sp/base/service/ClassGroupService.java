package ir.sp.base.service;

import ir.sp.base.domain.ClassGroup;
import ir.sp.base.domain.ClassTime;
import ir.sp.base.repository.ClassGroupRepository;
import ir.sp.base.repository.ClassRoomRepository;
import ir.sp.base.repository.ClassTimeRepository;
import ir.sp.base.service.dto.ClassGroupDTO;
import ir.sp.base.service.dto.ClassRoomDTO;
import ir.sp.base.service.mapper.ClassGroupMapper;
import ir.sp.base.service.mapper.ClassRoomMapper;
import ir.sp.base.service.mapper.ClassTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


/**
 * Service Implementation for managing ClassGroup.
 */
@Service
@Transactional
public class ClassGroupService {

    private final Logger log = LoggerFactory.getLogger(ClassGroupService.class);

    private final ClassGroupRepository classGroupRepository;

    private final ClassGroupMapper classGroupMapper;

    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomMapper classRoomMapper;

    private final ClassTimeRepository classTimeRepository;
    private final ClassTimeMapper classTimeMapper;

    public ClassGroupService(ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper, ClassRoomRepository classRoomRepository, ClassRoomMapper classRoomMapper, ClassTimeRepository classTimeRepository, ClassTimeMapper classTimeMapper) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
        this.classRoomRepository = classRoomRepository;
        this.classRoomMapper = classRoomMapper;
        this.classTimeRepository = classTimeRepository;
        this.classTimeMapper = classTimeMapper;
    }

    /**
     * Save a classGroup.
     *
     * @param classGroupDTO the entity to save
     * @return the persisted entity
     */
    public ClassGroupDTO save(ClassGroupDTO classGroupDTO) {
        log.debug("Request to save ClassGroup : {}", classGroupDTO);
        ClassGroup classGroup = classGroupMapper.toEntity(classGroupDTO);
        if (classGroupDTO.getId() != null)
            classTimeRepository.deleteAllByClassGroup_Id(classGroupDTO.getId());

        Set<ClassTime> preferenceTimes = classGroupDTO.getPreferenceTimes();
        classGroup = classGroupRepository.save(classGroup);
        ClassGroup finalClassGroup = classGroup;
        preferenceTimes.forEach(classTime -> {
            classTime.setId(null);
            classTime.setClassGroup(finalClassGroup);
        });
        classTimeRepository.save(preferenceTimes);
        return classGroupMapper.toDto(classGroup);
    }

    /**
     * Get all the classGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassGroups");
        return classGroupRepository.findAll(pageable)
            .map(classGroupMapper::toDto);
    }

    /**
     * Get one classGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClassGroupDTO findOne(Long id) {
        log.debug("Request to get ClassGroup : {}", id);
        ClassGroup classGroup = classGroupRepository.findOne(id);
        return classGroupMapper.toDto(classGroup);
    }

    /**
     * Delete the classGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassGroup : {}", id);
        classGroupRepository.delete(id);
    }

    public Page<ClassRoomDTO> findAllClassRooms(Long id, Pageable pageable) {
        return classRoomRepository.findAllByClassGroup_Id(id, pageable)
            .map(classRoomMapper::toDto);
    }
}
