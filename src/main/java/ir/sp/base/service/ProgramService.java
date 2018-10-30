package ir.sp.base.service;

import ir.sp.base.domain.Program;
import ir.sp.base.repository.ProgramRepository;
import ir.sp.base.service.dto.ProgramDTO;
import ir.sp.base.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramService.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    public ProgramService(ProgramRepository programRepository, ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
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
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable)
            .map(programMapper::toDto);
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
}
