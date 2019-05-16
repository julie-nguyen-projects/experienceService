package com.epitech.pgt2019.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Company entity.
 */
public class CompanyDTO implements Serializable {

    private String id;

    @NotNull
    private String name;


    private String cityExpId;

    private String cityExpName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityExpId() {
        return cityExpId;
    }

    public void setCityExpId(String cityExpId) {
        this.cityExpId = cityExpId;
    }

    public String getCityExpName() {
        return cityExpName;
    }

    public void setCityExpName(String cityExpName) {
        this.cityExpName = cityExpName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cityExp=" + getCityExpId() +
            ", cityExp='" + getCityExpName() + "'" +
            "}";
    }
}
