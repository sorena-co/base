package ir.sp.base.repository;

import ir.sp.base.domain.InstitutionPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the InstitutionPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionPersonRepository extends JpaRepository<InstitutionPerson, Long> {
    @Query("select distinct institution_person from InstitutionPerson institution_person left join fetch institution_person.courses")
    List<InstitutionPerson> findAllWithEagerRelationships();

    @Query("select institution_person from InstitutionPerson institution_person left join fetch institution_person.courses where institution_person.id =:id")
    InstitutionPerson findOneWithEagerRelationships(@Param("id") Long id);

}
