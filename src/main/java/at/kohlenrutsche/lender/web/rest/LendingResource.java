package at.kohlenrutsche.lender.web.rest;
import at.kohlenrutsche.lender.service.LendingService;
import at.kohlenrutsche.lender.web.rest.errors.BadRequestAlertException;
import at.kohlenrutsche.lender.web.rest.util.HeaderUtil;
import at.kohlenrutsche.lender.web.rest.util.PaginationUtil;
import at.kohlenrutsche.lender.service.dto.LendingDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Lending.
 */
@RestController
@RequestMapping("/api")
public class LendingResource {

    private final Logger log = LoggerFactory.getLogger(LendingResource.class);

    private static final String ENTITY_NAME = "lending";

    private final LendingService lendingService;

    public LendingResource(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    /**
     * POST  /lendings : Create a new lending.
     *
     * @param lendingDTO the lendingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lendingDTO, or with status 400 (Bad Request) if the lending has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lendings")
    public ResponseEntity<LendingDTO> createLending(@Valid @RequestBody LendingDTO lendingDTO) throws URISyntaxException {
        log.debug("REST request to save Lending : {}", lendingDTO);
        if (lendingDTO.getId() != null) {
            throw new BadRequestAlertException("A new lending cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LendingDTO result = lendingService.save(lendingDTO);
        return ResponseEntity.created(new URI("/api/lendings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lendings : Updates an existing lending.
     *
     * @param lendingDTO the lendingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lendingDTO,
     * or with status 400 (Bad Request) if the lendingDTO is not valid,
     * or with status 500 (Internal Server Error) if the lendingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lendings")
    public ResponseEntity<LendingDTO> updateLending(@Valid @RequestBody LendingDTO lendingDTO) throws URISyntaxException {
        log.debug("REST request to update Lending : {}", lendingDTO);
        if (lendingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LendingDTO result = lendingService.save(lendingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lendingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lendings : get all the lendings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lendings in body
     */
    @GetMapping("/lendings")
    public ResponseEntity<List<LendingDTO>> getAllLendings(Pageable pageable) {
        log.debug("REST request to get a page of Lendings");
        Page<LendingDTO> page = lendingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lendings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /lendings/:id : get the "id" lending.
     *
     * @param id the id of the lendingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lendingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lendings/{id}")
    public ResponseEntity<LendingDTO> getLending(@PathVariable Long id) {
        log.debug("REST request to get Lending : {}", id);
        Optional<LendingDTO> lendingDTO = lendingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lendingDTO);
    }

    /**
     * DELETE  /lendings/:id : delete the "id" lending.
     *
     * @param id the id of the lendingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lendings/{id}")
    public ResponseEntity<Void> deleteLending(@PathVariable Long id) {
        log.debug("REST request to delete Lending : {}", id);
        lendingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
