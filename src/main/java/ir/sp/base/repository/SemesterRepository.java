package ir.sp.base.repository;

import ir.sp.base.domain.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Semester entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    Page<Semester> findAllByInstitution_Id(Long institutionId, Pageable pageable);

}
