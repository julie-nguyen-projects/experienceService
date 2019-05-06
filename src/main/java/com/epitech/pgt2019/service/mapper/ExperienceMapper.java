package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.ExperienceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Experience and its DTO ExperienceDTO.
 */
@Mapper(componentModel = "spring", uses = {ExpUserMapper.class, CompanyMapper.class, SchoolMapper.class})
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "school.name", target = "schoolName")
    ExperienceDTO toDto(Experience experience);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "companyId", target = "company")
    @Mapping(source = "schoolId", target = "school")
    Experience toEntity(ExperienceDTO experienceDTO);

    default Experience fromId(String id) {
        if (id == null) {
            return null;
        }
        Experience experience = new Experience();
        experience.setId(id);
        return experience;
    }
}
