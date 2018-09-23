package ir.sp.base.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ClassPlanDTO {
    private Long id;
    private Long groupId;
    private Long courseId;
    private Long profId;
    private TimePlanDTO classTime;
    private List<TimePlanDTO> preferenceHours = new ArrayList<>();
    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getProfId() {
        return profId;
    }

    public void setProfId(Long profId) {
        this.profId = profId;
    }

    public TimePlanDTO getClassTime() {
        return classTime;
    }

    public void setClassTime(TimePlanDTO classTime) {
        this.classTime = classTime;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<TimePlanDTO> getPreferenceHours() {
        return preferenceHours;
    }

    public void setPreferenceHours(List<TimePlanDTO> preferenceHours) {
        this.preferenceHours = preferenceHours;
    }
}
