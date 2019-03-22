package ir.sp.base.repository;

import ir.sp.base.domain.InstitutionPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InstitutionPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionPersonRepository extends JpaRepository<InstitutionPerson, Long> {

}
