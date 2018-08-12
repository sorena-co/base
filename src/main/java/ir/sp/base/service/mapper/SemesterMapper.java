package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.SemesterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Semester and its DTO SemesterDTO.
 */
@Mapper(componentModel = "spring", uses = {InstitutionMapper.class})
public interface SemesterMapper extends EntityMapper<SemesterDTO, Semester> {

    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    SemesterDTO toDto(Semester semester);

    @Mapping(source = "institutionId", target = "institution")
    @Mapping(target = "classGroups", ignore = true)
    Semester toEntity(SemesterDTO semesterDTO);

    default Semester fromId(Long id) {
        if (id == null) {
            return null;
        }
        Semester semester = new Semester();
        semester.setId(id);
        return semester;
    }
}
