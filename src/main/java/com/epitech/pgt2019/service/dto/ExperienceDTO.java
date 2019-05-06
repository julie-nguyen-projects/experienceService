package com.epitech.pgt2019.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Experience entity.
 */
public class ExperienceDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    @NotNull
    private LocalDate beginningDate;

    private LocalDate endingDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExperienceDTO experienceDTO = (ExperienceDTO) o;
        if (experienceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experienceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperienceDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", beginningDate='" + getBeginningDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            "}";
    }
}
