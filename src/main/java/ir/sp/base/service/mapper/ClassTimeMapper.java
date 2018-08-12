package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.ClassTimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassTime and its DTO ClassTimeDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class, ClassGroupMapper.class})
public interface ClassTimeMapper extends EntityMapper<ClassTimeDTO, ClassTime> {

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.code", target = "personCode")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "classGroup.name", target = "classGroupName")
    ClassTimeDTO toDto(ClassTime classTime);

    @Mapping(source = "personId", target = "person")
    @Mapping(source = "classGroupId", target = "classGroup")
    ClassTime toEntity(ClassTimeDTO classTimeDTO);

    default ClassTime fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassTime classTime = new ClassTime();
        classTime.setId(id);
        return classTime;
    }
}
