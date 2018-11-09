package ir.sp.base.service.dto;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private Long id;
    private Long groupId;
    private String groupName;
    private Long courseId;
    private String courseName;
    private Long profId;
    private String profName;
    private ClassTimeDTO classTime;
    private List<ClassTimeDTO> preferenceHours = new ArrayList<>();
    private Long roomId;
    private String roomName;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getProfId() {
        return profId;
    }

    public void setProfId(Long profId) {
        this.profId = profId;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public ClassTimeDTO getClassTime() {
        return classTime;
    }

    public void setClassTime(ClassTimeDTO classTime) {
        this.classTime = classTime;
    }

    public List<ClassTimeDTO> getPreferenceHours() {
        return preferenceHours;
    }

    public void setPreferenceHours(List<ClassTimeDTO> preferenceHours) {
        this.preferenceHours = preferenceHours;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
