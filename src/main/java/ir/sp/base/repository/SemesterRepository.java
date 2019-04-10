package ir.sp.base.repository;

import ir.sp.base.domain.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Semester entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Page<Semester> findAllByInstitution_Id(Long institutionId, Pageable pageable);

    Boolean existsByTransId(String transId);

}
