package ir.sp.base.repository;

import ir.sp.base.domain.Course;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select distinct course from Course course left join fetch course.programs left join fetch course.people")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course left join fetch course.programs left join fetch course.people where course.id =:id")
    Course findOneWithEagerRelationships(@Param("id") Long id);

}
