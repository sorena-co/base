package ir.sp.base.service;

import ir.sp.base.domain.ClassRoom;
import ir.sp.base.repository.ClassRoomRepository;
import ir.sp.base.service.dto.ClassRoomDTO;
import ir.sp.base.service.mapper.ClassRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassRoom.
 */
@Service
@Transactional
public class ClassRoomService {

    private final Logger log = LoggerFactory.getLogger(ClassRoomService.class);

    private final ClassRoomRepository classRoomRepository;

    private final ClassRoomMapper classRoomMapper;

    public ClassRoomService(ClassRoomRepository classRoomRepository, ClassRoomMapper classRoomMapper) {
        this.classRoomRepository = classRoomRepository;
        this.classRoomMapper = classRoomMapper;
    }

    /**
     * Save a classRoom.
     *
     * @param classRoomDTO the entity to save
     * @return the persisted entity
     */
    public ClassRoomDTO save(ClassRoomDTO classRoomDTO) {
        log.debug("Request to save ClassRoom : {}", classRoomDTO);
        ClassRoom classRoom = classRoomMapper.toEntity(classRoomDTO);
        classRoom = classRoomRepository.save(classRoom);
        return classRoomMapper.toDto(classRoom);
    }

    /**
     * Get all the classRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ClassRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassRooms");
        return classRoomRepository.findAll(pageable)
            .map(classRoomMapper::toDto);
    }

    /**
     * Get one classRoom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ClassRoomDTO findOne(Long id) {
        log.debug("Request to get ClassRoom : {}", id);
        ClassRoom classRoom = classRoomRepository.findOne(id);
        return classRoomMapper.toDto(classRoom);
    }

    /**
     * Delete the classRoom by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassRoom : {}", id);
        classRoomRepository.delete(id);
    }
}
