package ir.sp.base.repository;

import ir.sp.base.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.nio.channels.FileChannel;
import java.util.List;


/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

/*    @Query(
        "select distinct course from Course course " +
            "inner join course.programs programs " +
            "inner join programs.institution institution " +
            "where " +
            "institution.id = :institutionId"
    )
    List<Course> findAllByInstitutionId(@Param("institutionId") Long institutionId);*/

    Page<Course> findAllByInstitution_Id(Long institutionId, Pageable pageable);
}
