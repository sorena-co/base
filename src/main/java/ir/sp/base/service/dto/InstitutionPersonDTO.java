package ir.sp.base.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InstitutionPerson entity.
 */
public class InstitutionPersonDTO implements Serializable {

    private Long id;

    private Long institutionId;

    private String institutionName;

    private Long personId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstitutionPersonDTO institutionPersonDTO = (InstitutionPersonDTO) o;
        if(institutionPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), institutionPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstitutionPersonDTO{" +
            "id=" + getId() +
            "}";
    }
}
