package com.epitech.pgt2019.service.mapper;

import com.epitech.pgt2019.domain.*;
import com.epitech.pgt2019.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {CityExpMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(source = "cityExp.id", target = "cityExpId")
    @Mapping(source = "cityExp.name", target = "cityExpName")
    CompanyDTO toDto(Company company);

    @Mapping(source = "cityExpId", target = "cityExp")
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(String id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
