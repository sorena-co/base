package ir.sp.base.repository;

import ir.sp.base.domain.Person;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("select distinct person from Person person left join fetch person.courses")
    List<Person> findAllWithEagerRelationships();

    @Query("select person from Person person left join fetch person.courses where person.id =:id")
    Person findOneWithEagerRelationships(@Param("id") Long id);

}
