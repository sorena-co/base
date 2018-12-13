package ir.sp.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClassRoom.
 */
@Entity
@Table(name = "class_room")
public class ClassRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(unique = true)
    private ClassTime classesTime;

    @OneToMany(mappedBy = "classRoom")
    @JsonIgnore
    private Set<ClassTime> preferenceTimes = new HashSet<>();

    @ManyToOne
    private ClassGroup classGroup;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Room room;

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

    public ClassRoom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ClassRoom code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ClassTime getClassesTime() {
        return classesTime;
    }

    public ClassRoom classesTime(ClassTime classTime) {
        this.classesTime = classTime;
        return this;
    }

    public void setClassesTime(ClassTime classTime) {
        this.classesTime = classTime;
    }

    public Set<ClassTime> getPreferenceTimes() {
        return preferenceTimes;
    }

    public ClassRoom preferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
        return this;
    }

    public ClassRoom addPreferenceTime(ClassTime classTime) {
        this.preferenceTimes.add(classTime);
        classTime.setClassRoom(this);
        return this;
    }

    public ClassRoom removePreferenceTime(ClassTime classTime) {
        this.preferenceTimes.remove(classTime);
        classTime.setClassRoom(null);
        return this;
    }

    public void setPreferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public ClassRoom classGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
        return this;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    public Person getPerson() {
        return person;
    }

    public ClassRoom person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Course getCourse() {
        return course;
    }

    public ClassRoom course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public ClassRoom room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
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
        ClassRoom classRoom = (ClassRoom) o;
        if (classRoom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classRoom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
