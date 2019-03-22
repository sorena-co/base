package ir.sp.base.repository;

import ir.sp.base.domain.InstitutionPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the InstitutionPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstitutionPersonRepository extends JpaRepository<InstitutionPerson, Long> {
    InstitutionPerson findFirstByInstitution_IdAndPerson_Id(Long institutionId, Long personId);
}
