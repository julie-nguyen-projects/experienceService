package com.epitech.pgt2019.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CountryExp entity.
 */
public class CountryExpDTO implements Serializable {

    private String id;

    
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CountryExpDTO countryExpDTO = (CountryExpDTO) o;
        if (countryExpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryExpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryExpDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
