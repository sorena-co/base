package ir.sp.base.repository;

import ir.sp.base.domain.ClassTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the ClassTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassTimeRepository extends JpaRepository<ClassTime, Long> {
    void deleteAllByClassGroup_Id(Long classGroupId);

    void deleteAllByInstitutionPerson_Id(Long institutionPersonId);
}
