package ir.sp.base.service.dto;

import java.util.ArrayList;
import java.util.List;

public class PlanDTO {
    private Long institutionId;
    private List<CoursePlanDTO> courses = new ArrayList<>();
    private List<ProfPlanDTO> profs = new ArrayList<>();
    private List<ClassPlanDTO> classes = new ArrayList<>();
    private List<RoomPlanDTO> rooms = new ArrayList<>();

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public List<CoursePlanDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CoursePlanDTO> courses) {
        this.courses = courses;
    }

    public List<ProfPlanDTO> getProfs() {
        return profs;
    }

    public void setProfs(List<ProfPlanDTO> profs) {
        this.profs = profs;
    }

    public List<ClassPlanDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassPlanDTO> classes) {
        this.classes = classes;
    }

    public List<RoomPlanDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomPlanDTO> rooms) {
        this.rooms = rooms;
    }
}

