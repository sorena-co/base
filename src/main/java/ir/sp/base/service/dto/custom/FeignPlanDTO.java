package ir.sp.base.service.dto.custom;


import java.util.ArrayList;
import java.util.List;

public class FeignPlanDTO {
    private List<ClassRoomDTO> classRooms = new ArrayList<>();
    private List<CourseDTO> courses = new ArrayList<>();
    private List<PersonDTO> persons = new ArrayList<>();
    private List<RoomDTO> rooms = new ArrayList<>();
    private Long institutionId;
    private Long semesterId;

    public List<ClassRoomDTO> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoomDTO> classRooms) {
        this.classRooms = classRooms;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }
}
