package com.epitech.pgt2019.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CityExp.
 */
@Document(collection = "city")
public class CityExp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @DBRef
    @Field("countryExp")
    @JsonIgnoreProperties("cityExps")
    private CountryExp countryExp;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CityExp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryExp getCountryExp() {
        return countryExp;
    }

    public CityExp countryExp(CountryExp countryExp) {
        this.countryExp = countryExp;
        return this;
    }

    public void setCountryExp(CountryExp countryExp) {
        this.countryExp = countryExp;
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
        CityExp cityExp = (CityExp) o;
        if (cityExp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityExp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityExp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
