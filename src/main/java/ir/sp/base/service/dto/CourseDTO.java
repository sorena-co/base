package ir.sp.base.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Course entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean needLab;

    private Boolean needProjector;

    private Integer practicalCredit;

    private Integer theoreticalCredit;

    private Integer practicalHours;

    private Integer theoreticalHours;

    private Long institutionId;

    private String institutionName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isNeedLab() {
        return needLab;
    }

    public void setNeedLab(Boolean needLab) {
        this.needLab = needLab;
    }

    public Boolean isNeedProjector() {
        return needProjector;
    }

    public void setNeedProjector(Boolean needProjector) {
        this.needProjector = needProjector;
    }

    public Integer getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(Integer practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public Integer getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public void setTheoreticalCredit(Integer theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public Integer getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
    }

    public Integer getTheoreticalHours() {
        return theoreticalHours;
    }

    public void setTheoreticalHours(Integer theoreticalHours) {
        this.theoreticalHours = theoreticalHours;
    }

    public Long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Long institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if(courseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", needLab='" + isNeedLab() + "'" +
            ", needProjector='" + isNeedProjector() + "'" +
            ", practicalCredit=" + getPracticalCredit() +
            ", theoreticalCredit=" + getTheoreticalCredit() +
            ", practicalHours=" + getPracticalHours() +
            ", theoreticalHours=" + getTheoreticalHours() +
            "}";
    }
}
