package ir.sp.base.repository;

import ir.sp.base.domain.ClassRoom;
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
        "select classRoom from ClassRoom classRoom " +
            "inner join classRoom.classGroup classGroup " +
            "inner join classGroup.program program " +
            "inner join program.institution institution " +
            "where institution.id = :institutionId"
    )
    List<ClassRoom> findAllClassRoomByInstitutionId(@Param("institutionId") Long institutionId);
}
