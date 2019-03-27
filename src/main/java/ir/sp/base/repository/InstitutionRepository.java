package ir.sp.base.repository;

import ir.sp.base.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Institution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    @Query(
        "select institution from Institution institution " +
            "inner join InstitutionPerson institutionPerson on institutionPerson.institution = institution " +
            "inner join institutionPerson.person person " +
            "where person.id = :personId"
    )
    List<Institution> findAllByPerson(@Param("personId") Long personId);

}
