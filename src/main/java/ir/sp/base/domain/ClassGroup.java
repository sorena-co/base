package ir.sp.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClassGroup.
 */
@Entity
@Table(name = "class_group")
public class ClassGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_size")
    private Integer size;

    @ManyToOne
    private Program program;

    @ManyToOne
    private Semester semester;

    @OneToMany(mappedBy = "classGroup")
    @JsonIgnore
    private Set<ClassTime> preferenceTimes = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    @JsonIgnore
    private Set<ClassRoom> classRooms = new HashSet<>();

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

    public ClassGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public ClassGroup size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Program getProgram() {
        return program;
    }

    public ClassGroup program(Program program) {
        this.program = program;
        return this;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Semester getSemester() {
        return semester;
    }

    public ClassGroup semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<ClassTime> getPreferenceTimes() {
        return preferenceTimes;
    }

    public ClassGroup preferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
        return this;
    }

    public ClassGroup addPreferenceTime(ClassTime classTime) {
        this.preferenceTimes.add(classTime);
        classTime.setClassGroup(this);
        return this;
    }

    public ClassGroup removePreferenceTime(ClassTime classTime) {
        this.preferenceTimes.remove(classTime);
        classTime.setClassGroup(null);
        return this;
    }

    public void setPreferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
    }

    public Set<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public ClassGroup classRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
        return this;
    }

    public ClassGroup addClassRooms(ClassRoom classRoom) {
        this.classRooms.add(classRoom);
        classRoom.setClassGroup(this);
        return this;
    }

    public ClassGroup removeClassRooms(ClassRoom classRoom) {
        this.classRooms.remove(classRoom);
        classRoom.setClassGroup(null);
        return this;
    }

    public void setClassRooms(Set<ClassRoom> classRooms) {
        this.classRooms = classRooms;
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
        ClassGroup classGroup = (ClassGroup) o;
        if (classGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", size=" + getSize() +
            "}";
    }
}
