package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.CountryExp;
import com.epitech.pgt2019.repository.CountryExpRepository;
import com.epitech.pgt2019.service.dto.CountryExpDTO;
import com.epitech.pgt2019.service.mapper.CountryExpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CountryExp.
 */
@Service
public class CountryExpService {

    private final Logger log = LoggerFactory.getLogger(CountryExpService.class);

    private final CountryExpRepository countryExpRepository;

    private final CountryExpMapper countryExpMapper;

    public CountryExpService(CountryExpRepository countryExpRepository, CountryExpMapper countryExpMapper) {
        this.countryExpRepository = countryExpRepository;
        this.countryExpMapper = countryExpMapper;
    }

    /**
     * Save a countryExp.
     *
     * @param countryExpDTO the entity to save
     * @return the persisted entity
     */
    public CountryExpDTO save(CountryExpDTO countryExpDTO) {
        log.debug("Request to save CountryExp : {}", countryExpDTO);
        CountryExp countryExp = countryExpMapper.toEntity(countryExpDTO);
        countryExp = countryExpRepository.save(countryExp);
        return countryExpMapper.toDto(countryExp);
    }

    /**
     * Get all the countryExps.
     *
     * @return the list of entities
     */
    public List<CountryExpDTO> findAll() {
        log.debug("Request to get all CountryExps");
        return countryExpRepository.findAll().stream()
            .map(countryExpMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one countryExp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CountryExpDTO> findOne(String id) {
        log.debug("Request to get CountryExp : {}", id);
        return countryExpRepository.findById(id)
            .map(countryExpMapper::toDto);
    }

    /**
     * Delete the countryExp by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CountryExp : {}", id);
        countryExpRepository.deleteById(id);
    }
}
