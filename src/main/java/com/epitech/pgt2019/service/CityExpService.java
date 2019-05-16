package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.CityExp;
import com.epitech.pgt2019.repository.CityExpRepository;
import com.epitech.pgt2019.service.dto.CityExpDTO;
import com.epitech.pgt2019.service.mapper.CityExpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CityExp.
 */
@Service
public class CityExpService {

    private final Logger log = LoggerFactory.getLogger(CityExpService.class);

    private final CityExpRepository cityExpRepository;

    private final CityExpMapper cityExpMapper;

    public CityExpService(CityExpRepository cityExpRepository, CityExpMapper cityExpMapper) {
        this.cityExpRepository = cityExpRepository;
        this.cityExpMapper = cityExpMapper;
    }

    /**
     * Save a cityExp.
     *
     * @param cityExpDTO the entity to save
     * @return the persisted entity
     */
    public CityExpDTO save(CityExpDTO cityExpDTO) {
        log.debug("Request to save CityExp : {}", cityExpDTO);
        CityExp cityExp = cityExpMapper.toEntity(cityExpDTO);
        cityExp = cityExpRepository.save(cityExp);
        return cityExpMapper.toDto(cityExp);
    }

    /**
     * Get all the cityExps.
     *
     * @return the list of entities
     */
    public List<CityExpDTO> findAll() {
        log.debug("Request to get all CityExps");
        return cityExpRepository.findAll().stream()
            .map(cityExpMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cityExp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<CityExpDTO> findOne(String id) {
        log.debug("Request to get CityExp : {}", id);
        return cityExpRepository.findById(id)
            .map(cityExpMapper::toDto);
    }

    /**
     * Delete the cityExp by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete CityExp : {}", id);
        cityExpRepository.deleteById(id);
    }
}
