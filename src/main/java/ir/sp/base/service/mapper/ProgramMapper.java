package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.ProgramDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Program and its DTO ProgramDTO.
 */
@Mapper(componentModel = "spring", uses = {InstitutionMapper.class})
public interface ProgramMapper extends EntityMapper<ProgramDTO, Program> {

    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    ProgramDTO toDto(Program program);

    @Mapping(source = "institutionId", target = "institution")
    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Program toEntity(ProgramDTO programDTO);

    default Program fromId(Long id) {
        if (id == null) {
            return null;
        }
        Program program = new Program();
        program.setId(id);
        return program;
    }
}
