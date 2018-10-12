package ir.sp.base.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "code")
    private String code;

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

    @ManyToMany
    @JoinTable(name = "course_program",
               joinColumns = @JoinColumn(name="courses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="programs_id", referencedColumnName="id"))
    private Set<Program> programs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "course_person",
               joinColumns = @JoinColumn(name="courses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="people_id", referencedColumnName="id"))
    private Set<Person> people = new HashSet<>();

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

    public String getCode() {
        return code;
    }

    public Course code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Set<Program> getPrograms() {
        return programs;
    }

    public Course programs(Set<Program> programs) {
        this.programs = programs;
        return this;
    }

    public Course addProgram(Program program) {
        this.programs.add(program);
        program.getCourses().add(this);
        return this;
    }

    public Course removeProgram(Program program) {
        this.programs.remove(program);
        program.getCourses().remove(this);
        return this;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Course people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public Course addPerson(Person person) {
        this.people.add(person);
        person.getCourses().add(this);
        return this;
    }

    public Course removePerson(Person person) {
        this.people.remove(person);
        person.getCourses().remove(this);
        return this;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
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
            ", code='" + getCode() + "'" +
            ", needLab='" + isNeedLab() + "'" +
            ", needProjector='" + isNeedProjector() + "'" +
            ", practicalCredit=" + getPracticalCredit() +
            ", theoreticalCredit=" + getTheoreticalCredit() +
            ", practicalHours=" + getPracticalHours() +
            ", theoreticalHours=" + getTheoreticalHours() +
            "}";
    }
}
