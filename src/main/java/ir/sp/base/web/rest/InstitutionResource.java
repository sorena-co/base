package ir.sp.base.web.rest;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.InstitutionService;
import ir.sp.base.service.dto.*;
import ir.sp.base.service.dto.feign.ClassRoomDTO;
import ir.sp.base.service.dto.feign.GaModel;
import ir.sp.base.service.util.Utils;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import ir.sp.base.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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


    @GetMapping("/institutions/start-plan/{id}")
    @Timed
    public ResponseEntity<String> startPlaning(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        String institutionDTO = institutionService.startPlaning(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(institutionDTO));
    }

    @GetMapping("/institutions/get-plan/{id}")
    @Timed
    public ResponseEntity<GaModel> getPlaning(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        GaModel institutionDTO = institutionService.getPlan(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(institutionDTO));
    }

    @GetMapping("/institutions/{institutionId}/programs")
    @Timed
    public ResponseEntity<List<ProgramDTO>> getAllPrograms(@PathVariable Long institutionId, Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<ProgramDTO> page = institutionService.findAllPrograms(institutionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/institutions/{institutionId}/people")
    @Timed
    public ResponseEntity<List<PersonDTO>> getAllPersons(@PathVariable Long institutionId, Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<PersonDTO> page = institutionService.findAllPersons(institutionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/institutions/{institutionId}/semesters")
    @Timed
    public ResponseEntity<List<SemesterDTO>> getAllSemester(@PathVariable Long institutionId, Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<SemesterDTO> page = institutionService.findAllSemester(institutionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/institutions/{institutionId}/rooms")
    @Timed
    public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable Long institutionId, Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<RoomDTO> page = institutionService.findAllRoom(institutionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/institutions/{institutionId}/courses")
    @Timed
    public ResponseEntity<List<CourseDTO>> getAllCourse(@PathVariable Long institutionId, Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<CourseDTO> page = institutionService.findAllCourses(institutionId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/institutions/{institutionId}/courses/all")
    @Timed
    public ResponseEntity<List<CourseDTO>> getAllCourseAll(@PathVariable Long institutionId) {
        log.debug("REST request to get a page of Institutions");
        List<CourseDTO> page = institutionService.findAllCourses(institutionId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/institutions/get-excel/{id}")
    @Timed
    @ResponseBody
    public ResponseEntity<Void> getExcel(HttpServletResponse response,
                                         @PathVariable Long id) throws IOException {
        log.debug("REST request to get Institution : {}", id);
        GaModel institutionDTO = institutionService.getPlan(id);

        Workbook workbook = new XSSFWorkbook();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("برنامه");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("گروه");
        cell0.setCellStyle(headerCellStyle);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("درس");
        cell1.setCellStyle(headerCellStyle);

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("استاد");
        cell2.setCellStyle(headerCellStyle);

        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("اتاق");
        cell3.setCellStyle(headerCellStyle);

        Cell cell4 = headerRow.createCell(4);
        cell4.setCellValue("روز");
        cell4.setCellStyle(headerCellStyle);

        Cell cell5 = headerRow.createCell(5);
        cell5.setCellValue("ساعت شروع");
        cell5.setCellStyle(headerCellStyle);

        Cell cell6 = headerRow.createCell(6);
        cell6.setCellValue("ساعت پایان");
        cell6.setCellStyle(headerCellStyle);


        int rowNum = 1;
        for (ClassRoomDTO entity : institutionDTO.getClassRooms()) {
            Row row = sheet.createRow(rowNum);

            row.createCell(0).setCellValue(entity.getClassGroupName());
            row.createCell(1).setCellValue(entity.getCourseName());
            row.createCell(2).setCellValue(entity.getPersonName());
            row.createCell(3).setCellValue(entity.getRoomName());
            row.createCell(4).setCellValue(Utils.getDayFA(entity.getDay()));
            row.createCell(5).setCellValue(entity.getStartTime());
            row.createCell(6).setCellValue(entity.getEndTime());
            rowNum++;
        }

        // Resize all columns to fit the content size
//        for (int i = 0; i < 6; i++) {
//            sheet.autoSizeColumn(i);
//        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.close();
        byte[] bytes = bos.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        response.setHeader("Content-Disposition", "attachment; filename=\"testExcel.xlsx\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);

        outputStream.close();
        inputStream.close();

        return ResponseEntity.ok().build();
    }

}
