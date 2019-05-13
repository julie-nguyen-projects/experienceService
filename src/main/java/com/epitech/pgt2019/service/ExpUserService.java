package com.epitech.pgt2019.service;

import com.epitech.pgt2019.domain.ExpUser;
import com.epitech.pgt2019.repository.ExpUserRepository;
import com.epitech.pgt2019.service.dto.ExpUserDTO;
import com.epitech.pgt2019.service.mapper.ExpUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ExpUser.
 */
@Service
public class ExpUserService {

    private final Logger log = LoggerFactory.getLogger(ExpUserService.class);

    private final ExpUserRepository expUserRepository;

    private final ExpUserMapper expUserMapper;

    public ExpUserService(ExpUserRepository expUserRepository, ExpUserMapper expUserMapper) {
        this.expUserRepository = expUserRepository;
        this.expUserMapper = expUserMapper;
    }

    /**
     * Save a expUser.
     *
     * @param expUserDTO the entity to save
     * @return the persisted entity
     */
    public ExpUserDTO save(ExpUserDTO expUserDTO) {
        log.debug("Request to save ExpUser : {}", expUserDTO);
        ExpUser expUser = expUserMapper.toEntity(expUserDTO);
        expUser = expUserRepository.save(expUser);
        return expUserMapper.toDto(expUser);
    }

    /**
     * Get all the expUsers.
     *
     * @return the list of entities
     */
    public List<ExpUserDTO> findAll() {
        log.debug("Request to get all ExpUsers");
        return expUserRepository.findAll().stream()
            .map(expUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one expUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<ExpUserDTO> findOne(String id) {
        log.debug("Request to get ExpUser : {}", id);
        return expUserRepository.findById(id)
            .map(expUserMapper::toDto);
    }

    /**
     * Delete the expUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ExpUser : {}", id);
        expUserRepository.deleteById(id);
    }
}
