package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.CityExpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CityExp and its DTO CityExpDTO.
 */
@Mapper(componentModel = "spring", uses = {CountryExpMapper.class})
public interface CityExpMapper extends EntityMapper<CityExpDTO, CityExp> {

    @Mapping(source = "countryExp.id", target = "countryExpId")
    @Mapping(source = "countryExp.name", target = "countryExpName")
    CityExpDTO toDto(CityExp cityExp);

    @Mapping(source = "countryExpId", target = "countryExp")
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
