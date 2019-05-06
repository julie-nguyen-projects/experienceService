package com.epitech.pgt2019.web.rest;
import com.epitech.pgt2019.service.ExperienceService;
import com.epitech.pgt2019.web.rest.errors.BadRequestAlertException;
import com.epitech.pgt2019.web.rest.util.HeaderUtil;
import com.epitech.pgt2019.service.dto.ExperienceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Experience.
 */
@RestController
@RequestMapping("/api")
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    private static final String ENTITY_NAME = "experienceServiceExperience";

    private final ExperienceService experienceService;

    public ExperienceResource(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    /**
     * POST  /experiences : Create a new experience.
     *
     * @param experienceDTO the experienceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experienceDTO, or with status 400 (Bad Request) if the experience has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experiences")
    public ResponseEntity<ExperienceDTO> createExperience(@Valid @RequestBody ExperienceDTO experienceDTO) throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experienceDTO);
        if (experienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new experience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceDTO result = experienceService.save(experienceDTO);
        return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experiences : Updates an existing experience.
     *
     * @param experienceDTO the experienceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experienceDTO,
     * or with status 400 (Bad Request) if the experienceDTO is not valid,
     * or with status 500 (Internal Server Error) if the experienceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experiences")
    public ResponseEntity<ExperienceDTO> updateExperience(@Valid @RequestBody ExperienceDTO experienceDTO) throws URISyntaxException {
        log.debug("REST request to update Experience : {}", experienceDTO);
        if (experienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExperienceDTO result = experienceService.save(experienceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experiences : get all the experiences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experiences in body
     */
    @GetMapping("/experiences")
    public List<ExperienceDTO> getAllExperiences() {
        log.debug("REST request to get all Experiences");
        return experienceService.findAll();
    }

    /**
     * GET  /experiences/:id : get the "id" experience.
     *
     * @param id the id of the experienceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experienceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/experiences/{id}")
    public ResponseEntity<ExperienceDTO> getExperience(@PathVariable String id) {
        log.debug("REST request to get Experience : {}", id);
        Optional<ExperienceDTO> experienceDTO = experienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(experienceDTO);
    }

    /**
     * DELETE  /experiences/:id : delete the "id" experience.
     *
     * @param id the id of the experienceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable String id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
