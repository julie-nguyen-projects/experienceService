package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.SchoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity School and its DTO SchoolDTO.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    SchoolDTO toDto(School school);

    @Mapping(source = "cityId", target = "city")
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
