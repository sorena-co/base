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

    @Query(
        "select distinct course from Semester semester " +
            "inner join semester.classGroups classGroups " +
            "inner join classGroups.classRooms classRooms " +
            "inner join classRooms.course course " +
            "where semester.id=:semesterId and semester.institution.id=:institutionId"
    )
    List<Course> findAllByInstitution_IdAndSemesterId(@Param("institutionId") Long institutionId, @Param("semesterId") Long semesterId);

    List<Course> findAllByIdIn(List<Long> courseIds);

    @Query(
        "select courses from Program program " +
            "inner join program.courses courses " +
            "where program.id=:programId"
    )
    List<Course> findAllByProgramId(@Param("programId") Long programId);

    List<Course> findAllByInstitution_Id(Long institutionId);
}
