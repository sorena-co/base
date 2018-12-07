package ir.sp.base.repository;

import ir.sp.base.domain.ClassGroup;
import ir.sp.base.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ClassGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Long> {
    Page<ClassGroup> findAllByProgram_Id(Long programId, Pageable pageable);

    Page<ClassGroup> findAllBySemester_Id(Long semesterId, Pageable pageable);

    List<ClassGroup> findAllByIdIn(List<Long> groupIds);
}
