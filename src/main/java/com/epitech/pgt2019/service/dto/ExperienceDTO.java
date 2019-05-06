package com.epitech.pgt2019.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.epitech.pgt2019.domain.enumeration.Type;

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

    @NotNull
    private Type type;


    private String userId;

    private String companyId;

    private String companyName;

    private String schoolId;

    private String schoolName;

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String expUserId) {
        this.userId = expUserId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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
            ", type='" + getType() + "'" +
            ", user=" + getUserId() +
            ", company=" + getCompanyId() +
            ", company='" + getCompanyName() + "'" +
            ", school=" + getSchoolId() +
            ", school='" + getSchoolName() + "'" +
            "}";
    }
}
