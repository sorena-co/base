package ir.sp.base.repository;

import ir.sp.base.domain.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ClassRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Long> {
    @Query(
        "select distinct classRoom from ClassRoom classRoom " +
            "inner join fetch classRoom.classGroup classGroup " +
            "inner join classGroup.program program " +
            "inner join classGroup.semester semester " +
            "inner join program.institution institution " +
            "left join fetch classGroup.preferenceTimes " +
            "where institution.id = :institutionId and semester.id=:semesterId"
    )
    List<ClassRoom> findAllClassRoomByInstitutionId(@Param("institutionId") Long institutionId, @Param("semesterId") Long semesterId);


    Page<ClassRoom> findAllByClassGroup_Id(Long classGroupId, Pageable pageable);

    List<ClassRoom> findAllByClassGroup_Id(Long classGroupId);

    void deleteAllByClassGroup_Id(Long classGroupId);
}
