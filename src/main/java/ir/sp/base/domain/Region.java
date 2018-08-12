package ir.sp.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "lvl")
    private String lvl;

    @ManyToOne
    private Region parentRegion;

    @OneToMany(mappedBy = "region")
    @JsonIgnore
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

    public Region name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Region code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLvl() {
        return lvl;
    }

    public Region lvl(String lvl) {
        this.lvl = lvl;
        return this;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public Region getParentRegion() {
        return parentRegion;
    }

    public Region parentRegion(Region region) {
        this.parentRegion = region;
        return this;
    }

    public void setParentRegion(Region region) {
        this.parentRegion = region;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Region people(Set<Person> people) {
        this.people = people;
        return this;
    }

    public Region addPerson(Person person) {
        this.people.add(person);
        person.setRegion(this);
        return this;
    }

    public Region removePerson(Person person) {
        this.people.remove(person);
        person.setRegion(null);
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
        Region region = (Region) o;
        if (region.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), region.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", lvl='" + getLvl() + "'" +
            "}";
    }
}
