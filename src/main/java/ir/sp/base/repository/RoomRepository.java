package ir.sp.base.repository;

import ir.sp.base.domain.Course;
import ir.sp.base.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.nio.channels.FileChannel;
import java.util.List;


/**
 * Spring Data JPA repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByInstitution_Id(Long institutionId);

    Page<Room> findAllByInstitution_Id(Long institutionId, Pageable pageable);

    List<Room> findAllByIdIn(List<Long> roomIds);
}
