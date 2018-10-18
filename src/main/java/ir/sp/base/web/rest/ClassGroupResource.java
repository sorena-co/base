package ir.sp.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.ClassGroupService;
import ir.sp.base.service.dto.ClassRoomDTO;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import ir.sp.base.web.rest.util.PaginationUtil;
import ir.sp.base.service.dto.ClassGroupDTO;
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
 * REST controller for managing ClassGroup.
 */
@RestController
@RequestMapping("/api")
public class ClassGroupResource {

    private final Logger log = LoggerFactory.getLogger(ClassGroupResource.class);

    private static final String ENTITY_NAME = "classGroup";

    private final ClassGroupService classGroupService;

    public ClassGroupResource(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    /**
     * POST  /class-groups : Create a new classGroup.
     *
     * @param classGroupDTO the classGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classGroupDTO, or with status 400 (Bad Request) if the classGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-groups")
    @Timed
    public ResponseEntity<ClassGroupDTO> createClassGroup(@RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new classGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.created(new URI("/api/class-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-groups : Updates an existing classGroup.
     *
     * @param classGroupDTO the classGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classGroupDTO,
     * or with status 400 (Bad Request) if the classGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the classGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-groups")
    @Timed
    public ResponseEntity<ClassGroupDTO> updateClassGroup(@RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() == null) {
            return createClassGroup(classGroupDTO);
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-groups : get all the classGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classGroups in body
     */
    @GetMapping("/class-groups")
    @Timed
    public ResponseEntity<List<ClassGroupDTO>> getAllClassGroups(Pageable pageable) {
        log.debug("REST request to get a page of ClassGroups");
        Page<ClassGroupDTO> page = classGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /class-groups/:id : get the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/class-groups/{id}")
    @Timed
    public ResponseEntity<ClassGroupDTO> getClassGroup(@PathVariable Long id) {
        log.debug("REST request to get ClassGroup : {}", id);
        ClassGroupDTO classGroupDTO = classGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classGroupDTO));
    }

    /**
     * DELETE  /class-groups/:id : delete the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassGroup(@PathVariable Long id) {
        log.debug("REST request to delete ClassGroup : {}", id);
        classGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/class-groups/{id}/class-rooms")
    @Timed
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRoom(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Class Room");
        Page<ClassRoomDTO> page = classGroupService.findAllClassRooms(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
