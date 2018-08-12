package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.ClassGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassGroup and its DTO ClassGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {ProgramMapper.class, SemesterMapper.class})
public interface ClassGroupMapper extends EntityMapper<ClassGroupDTO, ClassGroup> {

    @Mapping(source = "program.id", target = "programId")
    @Mapping(source = "program.name", target = "programName")
    @Mapping(source = "semester.id", target = "semesterId")
    @Mapping(source = "semester.name", target = "semesterName")
    ClassGroupDTO toDto(ClassGroup classGroup);

    @Mapping(source = "programId", target = "program")
    @Mapping(source = "semesterId", target = "semester")
    @Mapping(target = "preferenceTimes", ignore = true)
    @Mapping(target = "classRooms", ignore = true)
    ClassGroup toEntity(ClassGroupDTO classGroupDTO);

    default ClassGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(id);
        return classGroup;
    }
}
