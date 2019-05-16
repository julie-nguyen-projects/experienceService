package com.epitech.pgt2019.web.rest;
import com.epitech.pgt2019.service.CountryExpService;
import com.epitech.pgt2019.web.rest.errors.BadRequestAlertException;
import com.epitech.pgt2019.web.rest.util.HeaderUtil;
import com.epitech.pgt2019.service.dto.CountryExpDTO;
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
 * REST controller for managing CountryExp.
 */
@RestController
@RequestMapping("/api")
public class CountryExpResource {

    private final Logger log = LoggerFactory.getLogger(CountryExpResource.class);

    private static final String ENTITY_NAME = "experienceServiceCountryExp";

    private final CountryExpService countryExpService;

    public CountryExpResource(CountryExpService countryExpService) {
        this.countryExpService = countryExpService;
    }

    /**
     * POST  /country-exps : Create a new countryExp.
     *
     * @param countryExpDTO the countryExpDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countryExpDTO, or with status 400 (Bad Request) if the countryExp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/country-exps")
    public ResponseEntity<CountryExpDTO> createCountryExp(@Valid @RequestBody CountryExpDTO countryExpDTO) throws URISyntaxException {
        log.debug("REST request to save CountryExp : {}", countryExpDTO);
        if (countryExpDTO.getId() != null) {
            throw new BadRequestAlertException("A new countryExp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountryExpDTO result = countryExpService.save(countryExpDTO);
        return ResponseEntity.created(new URI("/api/country-exps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /country-exps : Updates an existing countryExp.
     *
     * @param countryExpDTO the countryExpDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countryExpDTO,
     * or with status 400 (Bad Request) if the countryExpDTO is not valid,
     * or with status 500 (Internal Server Error) if the countryExpDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/country-exps")
    public ResponseEntity<CountryExpDTO> updateCountryExp(@Valid @RequestBody CountryExpDTO countryExpDTO) throws URISyntaxException {
        log.debug("REST request to update CountryExp : {}", countryExpDTO);
        if (countryExpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountryExpDTO result = countryExpService.save(countryExpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, countryExpDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /country-exps : get all the countryExps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of countryExps in body
     */
    @GetMapping("/country-exps")
    public List<CountryExpDTO> getAllCountryExps() {
        log.debug("REST request to get all CountryExps");
        return countryExpService.findAll();
    }

    /**
     * GET  /country-exps/:id : get the "id" countryExp.
     *
     * @param id the id of the countryExpDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countryExpDTO, or with status 404 (Not Found)
     */
    @GetMapping("/country-exps/{id}")
    public ResponseEntity<CountryExpDTO> getCountryExp(@PathVariable String id) {
        log.debug("REST request to get CountryExp : {}", id);
        Optional<CountryExpDTO> countryExpDTO = countryExpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countryExpDTO);
    }

    /**
     * DELETE  /country-exps/:id : delete the "id" countryExp.
     *
     * @param id the id of the countryExpDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/country-exps/{id}")
    public ResponseEntity<Void> deleteCountryExp(@PathVariable String id) {
        log.debug("REST request to delete CountryExp : {}", id);
        countryExpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
