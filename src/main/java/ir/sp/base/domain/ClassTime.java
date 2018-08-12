package ir.sp.base.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import ir.sp.base.domain.enumeration.Day;

/**
 * A ClassTime.
 */
@Entity
@Table(name = "class_time")
public class ClassTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day;

    @Column(name = "start_time")
    private Float startTime;

    @Column(name = "end_time")
    private Float endTime;

    @Column(name = "priority")
    private Float priority;

    @ManyToOne
    private Person person;

    @ManyToOne
    private ClassGroup classGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public ClassTime day(Day day) {
        this.day = day;
        return this;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Float getStartTime() {
        return startTime;
    }

    public ClassTime startTime(Float startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Float startTime) {
        this.startTime = startTime;
    }

    public Float getEndTime() {
        return endTime;
    }

    public ClassTime endTime(Float endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Float endTime) {
        this.endTime = endTime;
    }

    public Float getPriority() {
        return priority;
    }

    public ClassTime priority(Float priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public Person getPerson() {
        return person;
    }

    public ClassTime person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public ClassTime classGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
        return this;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
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
        ClassTime classTime = (ClassTime) o;
        if (classTime.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classTime.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassTime{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", startTime=" + getStartTime() +
            ", endTime=" + getEndTime() +
            ", priority=" + getPriority() +
            "}";
    }
}
