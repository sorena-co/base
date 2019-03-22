package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.*;

import ir.sp.base.service.dto.custom.FeignPlanDTO;
import ir.sp.base.service.util.Utils;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

import static ir.sp.base.service.util.Utils.getDayNumber;

/**
 * Mapper for the entity Institution and its DTO InstitutionDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface InstitutionMapper extends EntityMapper<InstitutionDTO, Institution> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    InstitutionDTO toDto(Institution institution);

    @Mapping(source = "regionId", target = "region")
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

    default FeignPlanDTO toFeignPlanDTO(
        Long institutionId,
        List<Person> profs,
        List<Room> rooms,
        List<Course> courses,
        List<ClassRoom> classes,
        Semester semester) {
        FeignPlanDTO result = new FeignPlanDTO();
        result.setInstitutionId(institutionId);
        result.setSemesterId(semester.getId());
        classes.forEach(classRoom -> {
            ir.sp.base.service.dto.custom.ClassRoomDTO cls = new ir.sp.base.service.dto.custom.ClassRoomDTO();
            cls.setInstitutionId(institutionId);
            cls.setClassGroupId(classRoom.getClassGroup().getId());
            cls.setClassGroupName(classRoom.getClassGroup().getName());
            cls.setCourseId(classRoom.getCourse().getId());
            cls.setCourseName(classRoom.getCourse().getName());
            cls.setSetSize(classRoom.getSize());
            cls.setNeedLab(classRoom.getCourse().isNeedLab());
            cls.setNeedProjector(classRoom.getCourse().isNeedProjector());
            cls.setPracticalCredit(classRoom.getCourse().getPracticalCredit());
            cls.setPracticalHour(classRoom.getCourse().getPracticalHours());
            cls.setTheoreticalCredit(classRoom.getCourse().getTheoreticalCredit());
            cls.setTheoreticalHour(classRoom.getCourse().getTheoreticalHours());
            for (ClassTime classTime : classRoom.getClassGroup().getPreferenceTimes()) {
                ir.sp.base.service.dto.custom.ClassTime ct = new ir.sp.base.service.dto.custom.ClassTime();
                ct.setDay(getDayNumber(classTime.getDay()));
                ct.setEndTime(classTime.getEndTime());
                ct.setStartTime(classTime.getStartTime());
                ct.setPriority(classTime.getPriority());
                cls.getClassTimes().add(ct);
            }
            result.getClassRooms().add(cls);
        });
        courses.forEach(course -> {
            ir.sp.base.service.dto.custom.CourseDTO c = new ir.sp.base.service.dto.custom.CourseDTO();
            c.setId(course.getId());
            c.setName(course.getName());
            c.setNeedLab(course.isNeedLab());
            c.setNeedProjector(course.isNeedProjector());
            c.setPracticalCredit(course.getPracticalCredit());
            c.setPracticalHour(course.getPracticalHours());
            c.setTheoreticalCredit(course.getTheoreticalCredit());
            c.setTheoreticalHour(course.getTheoreticalHours());

            result.getCourses().add(c);
        });

        profs.forEach(prof -> {
            ir.sp.base.service.dto.custom.PersonDTO personDTO = new ir.sp.base.service.dto.custom.PersonDTO();
            personDTO.setId(prof.getId());
            personDTO.setPriority(prof.getPriority());
            personDTO.setName(prof.getFirstName() + " " + prof.getLastName());
            personDTO.setMaxCredits(prof.getMaxCredits());
            personDTO.setCourseIds(prof.getCourses().stream().map(Course::getId).collect(Collectors.toList()));

          /*  for (ClassTime classTime : prof.getPreferenceTimes()) {
                ir.sp.base.service.dto.custom.ClassTime ct = new ir.sp.base.service.dto.custom.ClassTime();
                ct.setDay(getDayNumber(classTime.getDay()));
                ct.setEndTime(classTime.getEndTime());
                ct.setStartTime(classTime.getStartTime());
                ct.setPriority(classTime.getPriority());
                personDTO.getClassTimes().add(ct);
            }*/
            result.getPersons().add(personDTO);
        });

        rooms.forEach(room -> {
            ir.sp.base.service.dto.custom.RoomDTO r = new ir.sp.base.service.dto.custom.RoomDTO();
            r.setId(room.getId());
            r.setHasProjector(room.isHasProjector());
            r.setLab(room.isIsLab());
            r.setName(room.getName());
            r.setSize(room.getSize());
            result.getRooms().add(r);
        });

        return result;
    }
}
