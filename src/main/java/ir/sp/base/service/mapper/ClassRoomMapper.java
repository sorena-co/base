package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.ClassRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassRoom and its DTO ClassRoomDTO.
 */
@Mapper(componentModel = "spring", uses = {ClassTimeMapper.class, ClassGroupMapper.class, PersonMapper.class, CourseMapper.class, RoomMapper.class})
public interface ClassRoomMapper extends EntityMapper<ClassRoomDTO, ClassRoom> {

    @Mapping(source = "classesTime.id", target = "classesTimeId")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "classGroup.name", target = "classGroupName")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.code", target = "personCode")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "classGroup.program.id", target = "programId")
    @Mapping(source = "classGroup.program.name", target = "programName")
    @Mapping(source = "classGroup.program.institution.id", target = "institutionId")
    @Mapping(source = "classGroup.program.institution.name", target = "institutionName")
    ClassRoomDTO toDto(ClassRoom classRoom);

    @Mapping(source = "classesTimeId", target = "classesTime")
    @Mapping(target = "preferenceTimes", ignore = true)
    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "personId", target = "person")
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "roomId", target = "room")
    ClassRoom toEntity(ClassRoomDTO classRoomDTO);

    default ClassRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(id);
        return classRoom;
    }
}
