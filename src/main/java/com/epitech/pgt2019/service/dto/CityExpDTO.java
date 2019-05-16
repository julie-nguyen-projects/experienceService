package com.epitech.pgt2019.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CityExp entity.
 */
public class CityExpDTO implements Serializable {

    private String id;

    private String name;


    private String countryExpId;

    private String countryExpName;

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

    public String getCountryExpId() {
        return countryExpId;
    }

    public void setCountryExpId(String countryExpId) {
        this.countryExpId = countryExpId;
    }

    public String getCountryExpName() {
        return countryExpName;
    }

    public void setCountryExpName(String countryExpName) {
        this.countryExpName = countryExpName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CityExpDTO cityExpDTO = (CityExpDTO) o;
        if (cityExpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityExpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityExpDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", countryExp=" + getCountryExpId() +
            ", countryExp='" + getCountryExpName() + "'" +
            "}";
    }
}
