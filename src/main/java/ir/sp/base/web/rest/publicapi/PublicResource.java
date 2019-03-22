package ir.sp.base.web.rest.publicapi;

import com.codahale.metrics.annotation.Timed;
import ir.sp.base.service.InstitutionService;
import ir.sp.base.service.dto.*;
import ir.sp.base.web.rest.errors.BadRequestAlertException;
import ir.sp.base.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing Institution.
 */
@RestController
@RequestMapping("/public")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(PublicResource.class);

    private static final String ENTITY_NAME = "institution";

    private final InstitutionService insService;

    public PublicResource(InstitutionService insService) {
        this.insService = insService;
    }

    @PostMapping("/institutions")
    @Timed
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody InstitutionDTO ins) throws URISyntaxException {
        log.debug("public REST request to save Institution : {}", ins);
        if (ins.getId() != null) {
            throw new BadRequestAlertException("A new institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitutionDTO result = insService.save(ins);
        return ResponseEntity.created(new URI("/api//" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
