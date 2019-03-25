package ir.sp.base.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import ir.sp.base.domain.enumeration.Gender;

import ir.sp.base.domain.enumeration.Degree;

import ir.sp.base.domain.enumeration.PersonType;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_degree")
    private Degree degree;

    @Column(name = "major")
    private String major;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "birth_certificate")
    private String birthCertificate;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "max_credits")
    private Integer maxCredits;

    @Column(name = "priority")
    private Float priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type")
    private PersonType personType;

    @ManyToOne
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Person fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Gender getGender() {
        return gender;
    }

    public Person gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Degree getDegree() {
        return degree;
    }

    public Person degree(Degree degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getMajor() {
        return major;
    }

    public Person major(String major) {
        this.major = major;
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Person nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getBirthCertificate() {
        return birthCertificate;
    }

    public Person birthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
        return this;
    }

    public void setBirthCertificate(String birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public Person birthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Person mobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Person postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public Person address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxCredits() {
        return maxCredits;
    }

    public Person maxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
        return this;
    }

    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }

    public Float getPriority() {
        return priority;
    }

    public Person priority(Float priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Float priority) {
        this.priority = priority;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public Person personType(PersonType personType) {
        this.personType = personType;
        return this;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public Region getRegion() {
        return region;
    }

    public Person region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
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
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", gender='" + getGender() + "'" +
            ", degree='" + getDegree() + "'" +
            ", major='" + getMajor() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", birthCertificate='" + getBirthCertificate() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", address='" + getAddress() + "'" +
            ", maxCredits=" + getMaxCredits() +
            ", priority=" + getPriority() +
            ", personType='" + getPersonType() + "'" +
            "}";
    }
}
