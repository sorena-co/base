package ir.sp.base.repository;

import ir.sp.base.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(
        "select person from InstitutionPerson institutionPerson " +
            "inner join institutionPerson.person person " +
            "inner join institutionPerson.institution institution " +
            "where institution.id = :institutionId "
    )
    List<Person> findAllByInstitution_Id(Long institutionId);

    @Query(
        "select person from InstitutionPerson institutionPerson " +
            "inner join institutionPerson.person person " +
            "inner join institutionPerson.institution institution " +
            "where institution.id = :institutionId "
    )
    Page<Person> findAllByInstitution_Id(@Param("institutionId") Long institutionId, Pageable pageable);

    List<Person> findAllByIdIn(List<Long> profIds);

    Person findFirstByEmail(String email);

    Person findFirstByNationalId(String nationalId);
}
