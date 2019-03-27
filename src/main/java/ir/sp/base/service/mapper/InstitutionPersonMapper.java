package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.InstitutionPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InstitutionPerson and its DTO InstitutionPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {InstitutionMapper.class, PersonMapper.class, CourseMapper.class})
public interface InstitutionPersonMapper extends EntityMapper<InstitutionPersonDTO, InstitutionPerson> {

    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    @Mapping(source = "person.id", target = "personId")
    InstitutionPersonDTO toDto(InstitutionPerson institutionPerson);

    @Mapping(target = "preferenceTimes", ignore = true)
    @Mapping(source = "institutionId", target = "institution")
    @Mapping(source = "personId", target = "person")
    InstitutionPerson toEntity(InstitutionPersonDTO institutionPersonDTO);

    default InstitutionPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        InstitutionPerson institutionPerson = new InstitutionPerson();
        institutionPerson.setId(id);
        return institutionPerson;
    }
}
