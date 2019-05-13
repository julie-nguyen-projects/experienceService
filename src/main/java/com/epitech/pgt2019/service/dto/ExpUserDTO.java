package com.epitech.pgt2019.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExpUser entity.
 */
public class ExpUserDTO implements Serializable {

    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpUserDTO expUserDTO = (ExpUserDTO) o;
        if (expUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpUserDTO{" +
            "id=" + getId() +
            "}";
    }
}
