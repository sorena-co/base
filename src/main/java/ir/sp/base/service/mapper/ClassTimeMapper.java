package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.ClassTimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassTime and its DTO ClassTimeDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassRoomMapper.class, ClassGroupMapper.class, InstitutionPersonMapper.class})
public interface ClassTimeMapper extends EntityMapper<ClassTimeDTO, ClassTime> {

    @Mapping(source = "classRoom.id", target = "classRoomId")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "classGroup.name", target = "classGroupName")
    @Mapping(source = "institutionPerson.id", target = "institutionPersonId")
    ClassTimeDTO toDto(ClassTime classTime);

    @Mapping(source = "classRoomId", target = "classRoom")
    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "institutionPersonId", target = "institutionPerson")
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
