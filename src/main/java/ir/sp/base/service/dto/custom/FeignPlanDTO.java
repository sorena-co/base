package ir.sp.base.service.dto.custom;


import java.util.ArrayList;
import java.util.List;

public class FeignPlanDTO {
    List<ClassRoomDTO> classRooms = new ArrayList<>();
    List<CourseDTO> courses = new ArrayList<>();
    List<PersonDTO> persons = new ArrayList<>();
    List<RoomDTO> rooms = new ArrayList<>();

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
}
