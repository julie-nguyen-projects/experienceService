package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.CountryExpDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CountryExp and its DTO CountryExpDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryExpMapper extends EntityMapper<CountryExpDTO, CountryExp> {



    default CountryExp fromId(String id) {
        if (id == null) {
            return null;
        }
        CountryExp countryExp = new CountryExp();
        countryExp.setId(id);
        return countryExp;
    }
}
