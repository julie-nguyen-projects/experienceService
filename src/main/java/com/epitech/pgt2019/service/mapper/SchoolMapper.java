package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.SchoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity School and its DTO SchoolDTO.
 */
@Mapper(componentModel = "spring", uses = {CityExpMapper.class})
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {

    @Mapping(source = "cityExp.id", target = "cityExpId")
    @Mapping(source = "cityExp.name", target = "cityExpName")
    SchoolDTO toDto(School school);

    @Mapping(source = "cityExpId", target = "cityExp")
    School toEntity(SchoolDTO schoolDTO);

    default School fromId(String id) {
        if (id == null) {
            return null;
        }
        School school = new School();
        school.setId(id);
        return school;
    }
}
