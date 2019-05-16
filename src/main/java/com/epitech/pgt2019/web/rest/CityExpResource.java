package com.epitech.pgt2019.web.rest;
import com.epitech.pgt2019.service.CityExpService;
import com.epitech.pgt2019.web.rest.errors.BadRequestAlertException;
import com.epitech.pgt2019.web.rest.util.HeaderUtil;
import com.epitech.pgt2019.service.dto.CityExpDTO;
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
 * REST controller for managing CityExp.
 */
@RestController
@RequestMapping("/api")
public class CityExpResource {

    private final Logger log = LoggerFactory.getLogger(CityExpResource.class);

    private static final String ENTITY_NAME = "experienceServiceCityExp";

    private final CityExpService cityExpService;

    public CityExpResource(CityExpService cityExpService) {
        this.cityExpService = cityExpService;
    }

    /**
     * POST  /city-exps : Create a new cityExp.
     *
     * @param cityExpDTO the cityExpDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cityExpDTO, or with status 400 (Bad Request) if the cityExp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/city-exps")
    public ResponseEntity<CityExpDTO> createCityExp(@Valid @RequestBody CityExpDTO cityExpDTO) throws URISyntaxException {
        log.debug("REST request to save CityExp : {}", cityExpDTO);
        if (cityExpDTO.getId() != null) {
            throw new BadRequestAlertException("A new cityExp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityExpDTO result = cityExpService.save(cityExpDTO);
        return ResponseEntity.created(new URI("/api/city-exps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /city-exps : Updates an existing cityExp.
     *
     * @param cityExpDTO the cityExpDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cityExpDTO,
     * or with status 400 (Bad Request) if the cityExpDTO is not valid,
     * or with status 500 (Internal Server Error) if the cityExpDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/city-exps")
    public ResponseEntity<CityExpDTO> updateCityExp(@Valid @RequestBody CityExpDTO cityExpDTO) throws URISyntaxException {
        log.debug("REST request to update CityExp : {}", cityExpDTO);
        if (cityExpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CityExpDTO result = cityExpService.save(cityExpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cityExpDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /city-exps : get all the cityExps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cityExps in body
     */
    @GetMapping("/city-exps")
    public List<CityExpDTO> getAllCityExps() {
        log.debug("REST request to get all CityExps");
        return cityExpService.findAll();
    }

    /**
     * GET  /city-exps/:id : get the "id" cityExp.
     *
     * @param id the id of the cityExpDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cityExpDTO, or with status 404 (Not Found)
     */
    @GetMapping("/city-exps/{id}")
    public ResponseEntity<CityExpDTO> getCityExp(@PathVariable String id) {
        log.debug("REST request to get CityExp : {}", id);
        Optional<CityExpDTO> cityExpDTO = cityExpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityExpDTO);
    }

    /**
     * DELETE  /city-exps/:id : delete the "id" cityExp.
     *
     * @param id the id of the cityExpDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/city-exps/{id}")
    public ResponseEntity<Void> deleteCityExp(@PathVariable String id) {
        log.debug("REST request to delete CityExp : {}", id);
        cityExpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
