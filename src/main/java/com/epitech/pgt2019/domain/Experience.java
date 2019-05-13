package com.epitech.pgt2019.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.epitech.pgt2019.domain.enumeration.Type;

/**
 * A Experience.
 */
@Document(collection = "experience")
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("beginning_date")
    private LocalDate beginningDate;

    @Field("ending_date")
    private LocalDate endingDate;

    @NotNull
    @Field("type")
    private Type type;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties("experiences")
    private ExpUser user;

    @DBRef
    @Field("company")
    @JsonIgnoreProperties("experiences")
    private Company company;

    @DBRef
    @Field("school")
    @JsonIgnoreProperties("experiences")
    private School school;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Experience title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public Experience beginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
        return this;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public Experience endingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
        return this;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Type getType() {
        return type;
    }

    public Experience type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ExpUser getUser() {
        return user;
    }

    public Experience user(ExpUser expUser) {
        this.user = expUser;
        return this;
    }

    public void setUser(ExpUser expUser) {
        this.user = expUser;
    }

    public Company getCompany() {
        return company;
    }

    public Experience company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public School getSchool() {
        return school;
    }

    public Experience school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
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
        Experience experience = (Experience) o;
        if (experience.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experience.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", beginningDate='" + getBeginningDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
