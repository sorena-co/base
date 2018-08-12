package ir.sp.base.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ir.sp.base.domain.enumeration.Day;

/**
 * A DTO for the ClassTime entity.
 */
public class ClassTimeDTO implements Serializable {

    private Long id;

    private Day day;

    private Float startTime;

    private Float endTime;

    private Float priority;

    private Long personId;

    private String personCode;

    private Long classGroupId;

    private String classGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Float getStartTime() {
        return startTime;
    }

    public void setStartTime(Float startTime) {
        this.startTime = startTime;
    }

    public Float getEndTime() {
        return endTime;
    }

    public void setEndTime(Float endTime) {
        this.endTime = endTime;
    }

    public Float getPriority() {
        return priority;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassTimeDTO classTimeDTO = (ClassTimeDTO) o;
        if(classTimeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classTimeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassTimeDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", startTime=" + getStartTime() +
            ", endTime=" + getEndTime() +
            ", priority=" + getPriority() +
            "}";
    }
}
