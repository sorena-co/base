package ir.sp.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ir.sp.base.domain.enumeration.InstitutionType;

/**
 * A Institution.
 */
@Entity
@Table(name = "institution")
public class Institution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "population")
    private Integer population;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "phone_number_1")
    private String phoneNumber1;

    @Column(name = "phone_number_2")
    private String phoneNumber2;

    @Column(name = "fax_number")
    private String faxNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "institution_type")
    private InstitutionType institutionType;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "institution")
    @JsonIgnore
    private Set<Person> persons = new HashSet<>();

    @OneToMany(mappedBy = "institution")
    @JsonIgnore
    private Set<Semester> semesters = new HashSet<>();

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

    public Institution name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Institution code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPopulation() {
        return population;
    }

    public Institution population(Integer population) {
        this.population = population;
        return this;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getEmail() {
        return email;
    }

    public Institution email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public Institution website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public Institution phoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
        return this;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public Institution phoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
        return this;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public Institution faxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Institution postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public Institution address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public Institution institutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
        return this;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public Region getRegion() {
        return region;
    }

    public Institution region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Institution persons(Set<Person> people) {
        this.persons = people;
        return this;
    }

    public Institution addPersons(Person person) {
        this.persons.add(person);
        person.setInstitution(this);
        return this;
    }

    public Institution removePersons(Person person) {
        this.persons.remove(person);
        person.setInstitution(null);
        return this;
    }

    public void setPersons(Set<Person> people) {
        this.persons = people;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public Institution semesters(Set<Semester> semesters) {
        this.semesters = semesters;
        return this;
    }

    public Institution addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.setInstitution(this);
        return this;
    }

    public Institution removeSemester(Semester semester) {
        this.semesters.remove(semester);
        semester.setInstitution(null);
        return this;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
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
        Institution institution = (Institution) o;
        if (institution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Institution{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", population=" + getPopulation() +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", phoneNumber1='" + getPhoneNumber1() + "'" +
            ", phoneNumber2='" + getPhoneNumber2() + "'" +
            ", faxNumber='" + getFaxNumber() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", institutionType='" + getInstitutionType() + "'" +
            "}";
    }
}
