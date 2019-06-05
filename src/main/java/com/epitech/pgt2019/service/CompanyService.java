package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.Company;
import com.epitech.pgt2019.repository.CompanyRepository;
import com.epitech.pgt2019.service.dto.CompanyDTO;
import com.epitech.pgt2019.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Company.
 */
@Service
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Get all the companies.
     *
     * @return the list of entities
     */
    public List<CompanyDTO> findAll() {
        log.debug("Request to get all Companies");
        return companyRepository.findAll().stream()
            .map(companyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one company by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CompanyDTO> findOne(String id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findById(id)
            .map(companyMapper::toDto);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }

    /**
     Get companies which name contains research parameter
     * @param name : text searched in name
     * @return : found companies
     */
    public List<CompanyDTO> findCompaniesNameContains(String name) {
        return companyRepository.findByNameContainsIgnoreCase(name).stream()
            .map(companyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Check if a company with the same name and with same city already exists
     * @param companyDTO : school to verify
     * @return true if already exists, else false
     */
    public boolean doesCompanyAlreadyExists(CompanyDTO companyDTO) {
        return companyRepository.findByNameIgnoreCaseAndCityExp(
            companyDTO.getName(),
            companyDTO.getCityExpId()
        ).isPresent();
    }
}
