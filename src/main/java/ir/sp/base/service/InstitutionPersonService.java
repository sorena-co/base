package ir.sp.base.service;

import ir.sp.base.domain.InstitutionPerson;
import ir.sp.base.repository.InstitutionPersonRepository;
import ir.sp.base.service.dto.InstitutionPersonDTO;
import ir.sp.base.service.mapper.InstitutionPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InstitutionPerson.
 */
@Service
@Transactional
public class InstitutionPersonService {

    private final Logger log = LoggerFactory.getLogger(InstitutionPersonService.class);

    private final InstitutionPersonRepository institutionPersonRepository;

    private final InstitutionPersonMapper institutionPersonMapper;

    public InstitutionPersonService(InstitutionPersonRepository institutionPersonRepository, InstitutionPersonMapper institutionPersonMapper) {
        this.institutionPersonRepository = institutionPersonRepository;
        this.institutionPersonMapper = institutionPersonMapper;
    }

    /**
     * Save a institutionPerson.
     *
     * @param institutionPersonDTO the entity to save
     * @return the persisted entity
     */
    public InstitutionPersonDTO save(InstitutionPersonDTO institutionPersonDTO) {
        log.debug("Request to save InstitutionPerson : {}", institutionPersonDTO);
        InstitutionPerson institutionPerson = institutionPersonMapper.toEntity(institutionPersonDTO);
        institutionPerson = institutionPersonRepository.save(institutionPerson);
        return institutionPersonMapper.toDto(institutionPerson);
    }

    /**
     * Get all the institutionPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InstitutionPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InstitutionPeople");
        return institutionPersonRepository.findAll(pageable)
            .map(institutionPersonMapper::toDto);
    }

    /**
     * Get one institutionPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InstitutionPersonDTO findOne(Long id) {
        log.debug("Request to get InstitutionPerson : {}", id);
        InstitutionPerson institutionPerson = institutionPersonRepository.findOne(id);
        return institutionPersonMapper.toDto(institutionPerson);
    }

    /**
     * Delete the institutionPerson by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InstitutionPerson : {}", id);
        institutionPersonRepository.delete(id);
    }
}
