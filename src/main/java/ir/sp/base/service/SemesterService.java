package ir.sp.base.service;

import com.querydsl.core.types.dsl.PathBuilder;
import ir.sp.base.domain.ClassGroup;
import ir.sp.base.domain.QClassGroup;
import ir.sp.base.domain.Semester;
import ir.sp.base.repository.ClassGroupRepository;
import ir.sp.base.repository.SemesterRepository;
import ir.sp.base.repository.dsl.PredicatesBuilder;
import ir.sp.base.service.dto.ClassGroupDTO;
import ir.sp.base.service.dto.SemesterDTO;
import ir.sp.base.service.dto.payment.PaymentResponseDTO;
import ir.sp.base.service.dto.payment.PrePaymentDTO;
import ir.sp.base.service.mapper.ClassGroupMapper;
import ir.sp.base.service.mapper.SemesterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;


/**
 * Service Implementation for managing Semester.
 */
@Service
@Transactional
public class SemesterService {

    private final Logger log = LoggerFactory.getLogger(SemesterService.class);

    private final SemesterRepository semesterRepository;

    private final SemesterMapper semesterMapper;

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupMapper classGroupMapper;

    public SemesterService(SemesterRepository semesterRepository, SemesterMapper semesterMapper, ClassGroupRepository classGroupRepository, ClassGroupMapper classGroupMapper) {
        this.semesterRepository = semesterRepository;
        this.semesterMapper = semesterMapper;
        this.classGroupRepository = classGroupRepository;
        this.classGroupMapper = classGroupMapper;
    }

    /**
     * Save a semester.
     *
     * @param semesterDTO the entity to save
     * @return the persisted entity
     */
    public SemesterDTO save(SemesterDTO semesterDTO) {
        log.debug("Request to save Semester : {}", semesterDTO);
        Semester semester = semesterMapper.toEntity(semesterDTO);
        semester = semesterRepository.save(semester);
        return semesterMapper.toDto(semester);
    }

    /**
     * Get all the semesters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SemesterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Semesters");
        return semesterRepository.findAll(pageable)
            .map(semesterMapper::toDto);
    }

    /**
     * Get one semester by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SemesterDTO findOne(Long id) {
        log.debug("Request to get Semester : {}", id);
        Semester semester = semesterRepository.findOne(id);
        return semesterMapper.toDto(semester);
    }

    /**
     * Delete the semester by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Semester : {}", id);
        semesterRepository.delete(id);
    }

    public Page<ClassGroupDTO> findAllClassGroups(Long id, String query, Pageable pageable) {
        Page<ClassGroup> result;
        if (query != null) {
            com.querydsl.core.types.dsl.BooleanExpression booleanExpression = new PredicatesBuilder().build(query, new PathBuilder<>(ClassGroup.class, "classGroup"), null);
            com.querydsl.core.types.dsl.BooleanExpression customerExpression = QClassGroup.classGroup.semester.id.eq(id);
            booleanExpression = booleanExpression != null ? booleanExpression.and(customerExpression) : customerExpression;
            result = classGroupRepository.findAll(booleanExpression, pageable);
        } else {
            result = classGroupRepository.findAllBySemester_Id(id, pageable);

        }
        return result.map(classGroupMapper::toDto);
    }

    public PaymentResponseDTO prePayment(PrePaymentDTO prePayment) {
        PaymentResponseDTO result;
        prePayment.setApi("test");
        String url = "https://pay.ir/pg/send";

        result = getPaymentResponseDTO(prePayment, url);

        return result;
    }

    public PaymentResponseDTO verify(PrePaymentDTO prePayment, Long institutionId) {
        PaymentResponseDTO result;
        prePayment.setApi("test");
        String url = "https://pay.ir/pg/verify";

        result = getPaymentResponseDTO(prePayment, url);

        Boolean existTransId = semesterRepository.existsByTransId(result.getTransId());

        if (result.getStatus().equals(1) && !existTransId) {
            SemesterDTO semesterDTO = new SemesterDTO();
            semesterDTO.setInstitutionId(institutionId);
            semesterDTO.setName("ترم جدید");
            semesterDTO.setStartDate(ZonedDateTime.now());
            semesterDTO.setEndDate(ZonedDateTime.now().plusDays(7));
            semesterDTO.setTransId(result.getTransId());
            this.save(semesterDTO);
        }
        return result;
    }

    private PaymentResponseDTO getPaymentResponseDTO(PrePaymentDTO prePayment, String url) {
        PaymentResponseDTO result;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PrePaymentDTO> request = new HttpEntity<>(prePayment);
        ResponseEntity<PaymentResponseDTO> response = restTemplate
            .exchange(url, HttpMethod.POST, request, PaymentResponseDTO.class);


        result = response.getBody();
        return result;
    }
}
