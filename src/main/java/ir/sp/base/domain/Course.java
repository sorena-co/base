package ir.sp.base.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "need_lab")
    private Boolean needLab;

    @Column(name = "need_projector")
    private Boolean needProjector;

    @Column(name = "practical_credit")
    private Integer practicalCredit;

    @Column(name = "theoretical_credit")
    private Integer theoreticalCredit;

    @Column(name = "practical_hours")
    private Integer practicalHours;

    @Column(name = "theoretical_hours")
    private Integer theoreticalHours;

    @ManyToOne
    private Institution institution;

    @ManyToOne
    private InstitutionPerson institutionPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isNeedLab() {
        return needLab;
    }

    public Course needLab(Boolean needLab) {
        this.needLab = needLab;
        return this;
    }

    public void setNeedLab(Boolean needLab) {
        this.needLab = needLab;
    }

    public Boolean isNeedProjector() {
        return needProjector;
    }

    public Course needProjector(Boolean needProjector) {
        this.needProjector = needProjector;
        return this;
    }

    public void setNeedProjector(Boolean needProjector) {
        this.needProjector = needProjector;
    }

    public Integer getPracticalCredit() {
        return practicalCredit;
    }

    public Course practicalCredit(Integer practicalCredit) {
        this.practicalCredit = practicalCredit;
        return this;
    }

    public void setPracticalCredit(Integer practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public Integer getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public Course theoreticalCredit(Integer theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
        return this;
    }

    public void setTheoreticalCredit(Integer theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public Integer getPracticalHours() {
        return practicalHours;
    }

    public Course practicalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
        return this;
    }

    public void setPracticalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
    }

    public Integer getTheoreticalHours() {
        return theoreticalHours;
    }

    public Course theoreticalHours(Integer theoreticalHours) {
        this.theoreticalHours = theoreticalHours;
        return this;
    }

    public void setTheoreticalHours(Integer theoreticalHours) {
        this.theoreticalHours = theoreticalHours;
    }

    public Institution getInstitution() {
        return institution;
    }

    public Course institution(Institution institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public InstitutionPerson getInstitutionPerson() {
        return institutionPerson;
    }

    public Course institutionPerson(InstitutionPerson institutionPerson) {
        this.institutionPerson = institutionPerson;
        return this;
    }

    public void setInstitutionPerson(InstitutionPerson institutionPerson) {
        this.institutionPerson = institutionPerson;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
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
