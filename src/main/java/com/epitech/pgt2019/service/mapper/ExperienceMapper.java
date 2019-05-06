package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.ExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Experience and its DTO ExperienceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {



    default Experience fromId(String id) {
        if (id == null) {
            return null;
        }
        Experience experience = new Experience();
        experience.setId(id);
        return experience;
    }
}
