package com.epitech.pgt2019.web.rest;
import com.epitech.pgt2019.service.ExpUserService;
import com.epitech.pgt2019.web.rest.errors.BadRequestAlertException;
import com.epitech.pgt2019.web.rest.util.HeaderUtil;
import com.epitech.pgt2019.service.dto.ExpUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExpUser.
 */
@RestController
@RequestMapping("/api")
public class ExpUserResource {

    private final Logger log = LoggerFactory.getLogger(ExpUserResource.class);

    private static final String ENTITY_NAME = "experienceServiceExpUser";

    private final ExpUserService expUserService;

    public ExpUserResource(ExpUserService expUserService) {
        this.expUserService = expUserService;
    }

    /**
     * POST  /exp-users : Create a new expUser.
     *
     * @param newId the new id of the expUser
     * @param expUserDTO the expUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expUserDTO, or with status 400 (Bad Request) if the expUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exp-users/{newId}")
    public ResponseEntity<ExpUserDTO> createExpUser(@PathVariable("newId") String newId, @RequestBody ExpUserDTO expUserDTO) throws URISyntaxException {
        log.debug("REST request to save ExpUser : {}", expUserDTO);
        if (expUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new expUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpUserDTO result = expUserService.save(newId, expUserDTO);
        return ResponseEntity.created(new URI("/api/exp-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exp-users : Updates an existing expUser.
     *
     * @param newId the newId of the expUser
     * @param expUserDTO the expUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expUserDTO,
     * or with status 400 (Bad Request) if the expUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the expUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exp-users/{newId}")
    public ResponseEntity<ExpUserDTO> updateExpUser(@PathVariable("newId") String newId, @RequestBody ExpUserDTO expUserDTO) throws URISyntaxException {
        log.debug("REST request to update ExpUser : {}", expUserDTO);
        if (expUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpUserDTO result = expUserService.save(newId, expUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exp-users : get all the expUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of expUsers in body
     */
    @GetMapping("/exp-users")
    public List<ExpUserDTO> getAllExpUsers() {
        log.debug("REST request to get all ExpUsers");
        return expUserService.findAll();
    }

    /**
     * GET  /exp-users/:id : get the "id" expUser.
     *
     * @param id the id of the expUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exp-users/{id}")
    public ResponseEntity<ExpUserDTO> getExpUser(@PathVariable String id) {
        log.debug("REST request to get ExpUser : {}", id);
        Optional<ExpUserDTO> expUserDTO = expUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expUserDTO);
    }

    /**
     * DELETE  /exp-users/:id : delete the "id" expUser.
     *
     * @param id the id of the expUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exp-users/{id}")
    public ResponseEntity<Void> deleteExpUser(@PathVariable String id) {
        log.debug("REST request to delete ExpUser : {}", id);
        expUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
