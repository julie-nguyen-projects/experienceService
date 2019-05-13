package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.Experience;
import com.epitech.pgt2019.repository.ExperienceRepository;
import com.epitech.pgt2019.service.dto.ExperienceDTO;
import com.epitech.pgt2019.service.mapper.ExperienceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Experience.
 */
@Service
public class ExperienceService {

    private final Logger log = LoggerFactory.getLogger(ExperienceService.class);

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    public ExperienceService(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    /**
     * Save a experience.
     *
     * @param experienceDTO the entity to save
     * @return the persisted entity
     */
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        log.debug("Request to save Experience : {}", experienceDTO);
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    /**
     * Get all the experiences.
     *
     * @return the list of entities
     */
    public List<ExperienceDTO> findAll() {
        log.debug("Request to get all Experiences");
        return experienceRepository.findAll().stream()
            .map(experienceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one experience by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<ExperienceDTO> findOne(String id) {
        log.debug("Request to get Experience : {}", id);
        return experienceRepository.findById(id)
            .map(experienceMapper::toDto);
    }

    /**
     * Delete the experience by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
    }
}
