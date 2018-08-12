package ir.sp.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.InstitutionService;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import ir.sp.base.web.rest.util.PaginationUtil;
import ir.sp.base.service.dto.InstitutionDTO;
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
 * REST controller for managing Institution.
 */
@RestController
@RequestMapping("/api")
public class InstitutionResource {

    private final Logger log = LoggerFactory.getLogger(InstitutionResource.class);

    private static final String ENTITY_NAME = "institution";

    private final InstitutionService institutionService;

    public InstitutionResource(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    /**
     * POST  /institutions : Create a new institution.
     *
     * @param institutionDTO the institutionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new institutionDTO, or with status 400 (Bad Request) if the institution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/institutions")
    @Timed
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody InstitutionDTO institutionDTO) throws URISyntaxException {
        log.debug("REST request to save Institution : {}", institutionDTO);
        if (institutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitutionDTO result = institutionService.save(institutionDTO);
        return ResponseEntity.created(new URI("/api/institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institutions : Updates an existing institution.
     *
     * @param institutionDTO the institutionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated institutionDTO,
     * or with status 400 (Bad Request) if the institutionDTO is not valid,
     * or with status 500 (Internal Server Error) if the institutionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/institutions")
    @Timed
    public ResponseEntity<InstitutionDTO> updateInstitution(@RequestBody InstitutionDTO institutionDTO) throws URISyntaxException {
        log.debug("REST request to update Institution : {}", institutionDTO);
        if (institutionDTO.getId() == null) {
            return createInstitution(institutionDTO);
        }
        InstitutionDTO result = institutionService.save(institutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, institutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institutions : get all the institutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of institutions in body
     */
    @GetMapping("/institutions")
    @Timed
    public ResponseEntity<List<InstitutionDTO>> getAllInstitutions(Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<InstitutionDTO> page = institutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /institutions/:id : get the "id" institution.
     *
     * @param id the id of the institutionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the institutionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/institutions/{id}")
    @Timed
    public ResponseEntity<InstitutionDTO> getInstitution(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        InstitutionDTO institutionDTO = institutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(institutionDTO));
    }

    /**
     * DELETE  /institutions/:id : delete the "id" institution.
     *
     * @param id the id of the institutionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/institutions/{id}")
    @Timed
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        log.debug("REST request to delete Institution : {}", id);
        institutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
