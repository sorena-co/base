package ir.sp.base.service.mapper;

import ir.sp.base.domain.*;
import ir.sp.base.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", uses = {InstitutionMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    CourseDTO toDto(Course course);

    @Mapping(source = "institutionId", target = "institution")
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
