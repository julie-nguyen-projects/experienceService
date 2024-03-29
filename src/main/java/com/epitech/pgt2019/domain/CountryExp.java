package com.epitech.pgt2019.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CountryExp.
 */
@Document(collection = "country")
public class CountryExp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    
    @Field("name")
    private String name;

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

    public CountryExp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        CountryExp countryExp = (CountryExp) o;
        if (countryExp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryExp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryExp{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
