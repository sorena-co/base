package ir.sp.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.InstitutionPersonService;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import ir.sp.base.web.rest.util.PaginationUtil;
import ir.sp.base.service.dto.InstitutionPersonDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InstitutionPerson.
 */
@RestController
@RequestMapping("/api")
public class InstitutionPersonResource {

    private final Logger log = LoggerFactory.getLogger(InstitutionPersonResource.class);

    private static final String ENTITY_NAME = "institutionPerson";

    private final InstitutionPersonService institutionPersonService;

    public InstitutionPersonResource(InstitutionPersonService institutionPersonService) {
        this.institutionPersonService = institutionPersonService;
    }

    /**
     * POST  /institution-people : Create a new institutionPerson.
     *
     * @param institutionPersonDTO the institutionPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new institutionPersonDTO, or with status 400 (Bad Request) if the institutionPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/institution-people")
    @Timed
    public ResponseEntity<InstitutionPersonDTO> createInstitutionPerson(@RequestBody InstitutionPersonDTO institutionPersonDTO) throws URISyntaxException {
        log.debug("REST request to save InstitutionPerson : {}", institutionPersonDTO);
        if (institutionPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new institutionPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitutionPersonDTO result = institutionPersonService.save(institutionPersonDTO);
        return ResponseEntity.created(new URI("/api/institution-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institution-people : Updates an existing institutionPerson.
     *
     * @param institutionPersonDTO the institutionPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated institutionPersonDTO,
     * or with status 400 (Bad Request) if the institutionPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the institutionPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/institution-people")
    @Timed
    public ResponseEntity<InstitutionPersonDTO> updateInstitutionPerson(@RequestBody InstitutionPersonDTO institutionPersonDTO) throws URISyntaxException {
        log.debug("REST request to update InstitutionPerson : {}", institutionPersonDTO);
        if (institutionPersonDTO.getId() == null) {
            return createInstitutionPerson(institutionPersonDTO);
        }
        InstitutionPersonDTO result = institutionPersonService.save(institutionPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, institutionPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institution-people : get all the institutionPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of institutionPeople in body
     */
    @GetMapping("/institution-people")
    @Timed
    public ResponseEntity<List<InstitutionPersonDTO>> getAllInstitutionPeople(Pageable pageable) {
        log.debug("REST request to get a page of InstitutionPeople");
        Page<InstitutionPersonDTO> page = institutionPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institution-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /institution-people/:id : get the "id" institutionPerson.
     *
     * @param id the id of the institutionPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the institutionPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/institution-people/{id}")
    @Timed
    public ResponseEntity<InstitutionPersonDTO> getInstitutionPerson(@PathVariable Long id) {
        log.debug("REST request to get InstitutionPerson : {}", id);
        InstitutionPersonDTO institutionPersonDTO = institutionPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(institutionPersonDTO));
    }

    /**
     * DELETE  /institution-people/:id : delete the "id" institutionPerson.
     *
     * @param id the id of the institutionPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/institution-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteInstitutionPerson(@PathVariable Long id) {
        log.debug("REST request to delete InstitutionPerson : {}", id);
        institutionPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
