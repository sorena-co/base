package ir.sp.base.repository;

import ir.sp.base.domain.InstitutionPerson;
import ir.sp.base.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the InstitutionPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionPersonRepository extends JpaRepository<InstitutionPerson, Long> {
    InstitutionPerson findFirstByInstitution_IdAndPerson_Id(Long institutionId, Long personId);

    @Query(
        "select ip from InstitutionPerson ip " +
            "inner join fetch ip.person person " +
            "inner join fetch ip.institution institution " +
            "left join fetch ip.courses courses " +
            "left join fetch ip.preferenceTimes preferenceTimes " +
            "where institution.id = :institutionId and person.id = :personId "
    )
    InstitutionPerson findFirstByInstitution_IdAndPerson_IdWithFetch(@Param("institutionId") Long institutionId, @Param("personId") Long personId);

    List<InstitutionPerson> findAllByInstitution_IdAndPersonIn(Long institutionId, List<Person> profs);
}
