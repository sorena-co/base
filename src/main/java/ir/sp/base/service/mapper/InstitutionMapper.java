package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.*;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

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

    default PlanDTO toPlanDTO(
        Long institutionId,
        List<Person> profs,
        List<Room> rooms,
        List<Course> courses,
        List<ClassRoom> classes
    ) {
        PlanDTO result = new PlanDTO();
        //fill institution
        result.setInstitutionId(institutionId);
        //end

        //fill profs
        profs.forEach(person -> {
            ProfPlanDTO dto = new ProfPlanDTO();
            dto.setId(person.getId());
            dto.setCourseIds(person.getCourses().stream().map(Course::getId).collect(Collectors.toList()));
            dto.setMaxCredits(person.getMaxCredits());
            if (!person.getPreferenceTimes().isEmpty()) {
                person.getPreferenceTimes().forEach(classTime -> {
                    TimePlanDTO time = getTime(classTime, dto.getPreferenceHours());
                    dto.getPreferenceHours().add(time);
                });
            }
            result.getProfs().add(dto);
        });
        //end profs

        //fill classes
        classes.forEach(classGroup -> {
            ClassPlanDTO dto = new ClassPlanDTO();
            dto.setGroupId(classGroup.getClassGroup().getId());
            dto.setCourseId(classGroup.getCourse().getId());
            if (classGroup.getClassesTime() != null) {
                dto.setClassTime(new TimePlanDTO(
                    classGroup.getClassesTime().getStartTime(),
                    classGroup.getClassesTime().getEndTime(),
                    classGroup.getClassesTime().getDay().ordinal()
                ));
            }
            dto.setId(classGroup.getId());
            if (classGroup.getPerson() != null)
                dto.setProfId(classGroup.getPerson().getId());
            if (classGroup.getRoom() != null)
                dto.setRoomId(classGroup.getRoom().getId());
            if (!classGroup.getClassGroup().getPreferenceTimes().isEmpty()) {
                classGroup.getClassGroup().getPreferenceTimes().forEach(classTime -> {
                    TimePlanDTO timeDTO = getTime(classTime, dto.getPreferenceHours());
                    dto.getPreferenceHours().add(timeDTO);
                });
            }
            result.getClasses().add(dto);
        });
        //end classes

        //fill rooms
        rooms.forEach(room -> {
            RoomPlanDTO dto = new RoomPlanDTO();
            dto.setId(room.getId());
            dto.setLab(room.isIsLab());
            dto.setProjector(room.isHasProjector());
            dto.setSize(room.getSize());

            result.getRooms().add(dto);
        });
        //end rooms

        //fill courses
        courses.forEach(course -> {
            CoursePlanDTO dto = new CoursePlanDTO();
            dto.setId(course.getId());
            dto.setNeedLab(course.isNeedLab());
            dto.setNeedProjector(course.isNeedProjector());
            dto.setPracticalCredit(course.getPracticalCredit());
            dto.setPracticalHours(course.getPracticalHours());
            dto.setTheoreticalCredit(course.getTheoreticalCredit());
            dto.setTheoreticalHours(course.getTheoreticalHours());

            result.getCourses().add(dto);
        });
        //end courses
        return result;
    }

    default TimePlanDTO getTime(ClassTime classTime, List<TimePlanDTO> preferenceHours) {
        TimePlanDTO timeDTO = new TimePlanDTO();
        timeDTO.setDay(classTime.getDay().ordinal());
        timeDTO.setStartTime(classTime.getStartTime());
        timeDTO.setEndTime(classTime.getEndTime());
        preferenceHours.add(timeDTO);
        return timeDTO;
    }
}
