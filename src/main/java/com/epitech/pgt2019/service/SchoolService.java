package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.School;
import com.epitech.pgt2019.repository.SchoolRepository;
import com.epitech.pgt2019.service.dto.SchoolDTO;
import com.epitech.pgt2019.service.mapper.SchoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing School.
 */
@Service
public class SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolService.class);

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save
     * @return the persisted entity
     */
    public SchoolDTO save(SchoolDTO schoolDTO) {
        log.debug("Request to save School : {}", schoolDTO);
        School school = schoolMapper.toEntity(schoolDTO);
        school = schoolRepository.save(school);
        return schoolMapper.toDto(school);
    }

    /**
     * Get all the schools.
     *
     * @return the list of entities
     */
    public List<SchoolDTO> findAll() {
        log.debug("Request to get all Schools");
        return schoolRepository.findAll().stream()
            .map(schoolMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one school by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<SchoolDTO> findOne(String id) {
        log.debug("Request to get School : {}", id);
        return schoolRepository.findById(id)
            .map(schoolMapper::toDto);
    }

    /**
     * Delete the school by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.deleteById(id);
    }

    public List<SchoolDTO> findSchoolsNameContains(String name) {
        return schoolRepository.findByNameContainsIgnoreCase(name)
            .stream()
            .map(schoolMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
