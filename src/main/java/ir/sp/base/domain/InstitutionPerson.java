package ir.sp.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstitutionPerson.
 */
@Entity
@Table(name = "institution_person")
public class InstitutionPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "institutionPerson")
    @JsonIgnore
    private Set<ClassTime> preferenceTimes = new HashSet<>();

    @ManyToOne
    private Institution institution;

    @ManyToOne
    private Person person;

    @ManyToMany
    @JoinTable(name = "institution_person_course",
               joinColumns = @JoinColumn(name="institution_people_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="courses_id", referencedColumnName="id"))
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ClassTime> getPreferenceTimes() {
        return preferenceTimes;
    }

    public InstitutionPerson preferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
        return this;
    }

    public InstitutionPerson addPreferenceTime(ClassTime classTime) {
        this.preferenceTimes.add(classTime);
        classTime.setInstitutionPerson(this);
        return this;
    }

    public InstitutionPerson removePreferenceTime(ClassTime classTime) {
        this.preferenceTimes.remove(classTime);
        classTime.setInstitutionPerson(null);
        return this;
    }

    public void setPreferenceTimes(Set<ClassTime> classTimes) {
        this.preferenceTimes = classTimes;
    }

    public Institution getInstitution() {
        return institution;
    }

    public InstitutionPerson institution(Institution institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Person getPerson() {
        return person;
    }

    public InstitutionPerson person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public InstitutionPerson courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public InstitutionPerson addCourse(Course course) {
        this.courses.add(course);
        return this;
    }

    public InstitutionPerson removeCourse(Course course) {
        this.courses.remove(course);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
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
        InstitutionPerson institutionPerson = (InstitutionPerson) o;
        if (institutionPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institutionPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstitutionPerson{" +
            "id=" + getId() +
            "}";
    }
}
