package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.InstitutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Institution and its DTO InstitutionDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface InstitutionMapper extends EntityMapper<InstitutionDTO, Institution> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    InstitutionDTO toDto(Institution institution);

    @Mapping(source = "regionId", target = "region")
    @Mapping(target = "persons", ignore = true)
    @Mapping(target = "semesters", ignore = true)
    Institution toEntity(InstitutionDTO institutionDTO);

    default Institution fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institution institution = new Institution();
        institution.setId(id);
        return institution;
    }
}
