package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.CityExpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CityExp and its DTO CityExpDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface CityExpMapper extends EntityMapper<CityExpDTO, CityExp> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CityExpDTO toDto(CityExp cityExp);

    @Mapping(source = "countryId", target = "country")
    CityExp toEntity(CityExpDTO cityExpDTO);

    default CityExp fromId(String id) {
        if (id == null) {
            return null;
        }
        CityExp cityExp = new CityExp();
        cityExp.setId(id);
        return cityExp;
    }
}
