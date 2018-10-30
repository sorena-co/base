package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.PersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, InstitutionMapper.class, CourseMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    PersonDTO toDto(Person person);

    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "institutionId", target = "institution")
    @Mapping(target = "preferenceTimes", ignore = true)
    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }
}
