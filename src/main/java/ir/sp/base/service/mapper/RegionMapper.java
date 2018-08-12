package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.RegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Region and its DTO RegionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {

    @Mapping(source = "parentRegion.id", target = "parentRegionId")
    @Mapping(source = "parentRegion.name", target = "parentRegionName")
    RegionDTO toDto(Region region);

    @Mapping(source = "parentRegionId", target = "parentRegion")
    @Mapping(target = "people", ignore = true)
    Region toEntity(RegionDTO regionDTO);

    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}
