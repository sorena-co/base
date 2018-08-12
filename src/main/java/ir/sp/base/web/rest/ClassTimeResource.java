package ir.sp.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.ClassTimeService;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import ir.sp.base.web.rest.util.PaginationUtil;
import ir.sp.base.service.dto.ClassTimeDTO;
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
 * REST controller for managing ClassTime.
 */
@RestController
@RequestMapping("/api")
public class ClassTimeResource {

    private final Logger log = LoggerFactory.getLogger(ClassTimeResource.class);

    private static final String ENTITY_NAME = "classTime";

    private final ClassTimeService classTimeService;

    public ClassTimeResource(ClassTimeService classTimeService) {
        this.classTimeService = classTimeService;
    }

    /**
     * POST  /class-times : Create a new classTime.
     *
     * @param classTimeDTO the classTimeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classTimeDTO, or with status 400 (Bad Request) if the classTime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-times")
    @Timed
    public ResponseEntity<ClassTimeDTO> createClassTime(@RequestBody ClassTimeDTO classTimeDTO) throws URISyntaxException {
        log.debug("REST request to save ClassTime : {}", classTimeDTO);
        if (classTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new classTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassTimeDTO result = classTimeService.save(classTimeDTO);
        return ResponseEntity.created(new URI("/api/class-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-times : Updates an existing classTime.
     *
     * @param classTimeDTO the classTimeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classTimeDTO,
     * or with status 400 (Bad Request) if the classTimeDTO is not valid,
     * or with status 500 (Internal Server Error) if the classTimeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-times")
    @Timed
    public ResponseEntity<ClassTimeDTO> updateClassTime(@RequestBody ClassTimeDTO classTimeDTO) throws URISyntaxException {
        log.debug("REST request to update ClassTime : {}", classTimeDTO);
        if (classTimeDTO.getId() == null) {
            return createClassTime(classTimeDTO);
        }
        ClassTimeDTO result = classTimeService.save(classTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classTimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-times : get all the classTimes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classTimes in body
     */
    @GetMapping("/class-times")
    @Timed
    public ResponseEntity<List<ClassTimeDTO>> getAllClassTimes(Pageable pageable) {
        log.debug("REST request to get a page of ClassTimes");
        Page<ClassTimeDTO> page = classTimeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-times");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-times/:id : get the "id" classTime.
     *
     * @param id the id of the classTimeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classTimeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/class-times/{id}")
    @Timed
    public ResponseEntity<ClassTimeDTO> getClassTime(@PathVariable Long id) {
        log.debug("REST request to get ClassTime : {}", id);
        ClassTimeDTO classTimeDTO = classTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classTimeDTO));
    }

    /**
     * DELETE  /class-times/:id : delete the "id" classTime.
     *
     * @param id the id of the classTimeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-times/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassTime(@PathVariable Long id) {
        log.debug("REST request to delete ClassTime : {}", id);
        classTimeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
