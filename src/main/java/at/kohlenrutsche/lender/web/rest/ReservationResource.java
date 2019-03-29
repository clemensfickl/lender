package at.kohlenrutsche.lender.web.rest;
import at.kohlenrutsche.lender.service.ReservationService;
import at.kohlenrutsche.lender.web.rest.errors.BadRequestAlertException;
import at.kohlenrutsche.lender.web.rest.util.HeaderUtil;
import at.kohlenrutsche.lender.web.rest.util.PaginationUtil;
import at.kohlenrutsche.lender.service.dto.ReservationDTO;
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
 * REST controller for managing Reservation.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    private static final String ENTITY_NAME = "reservation";

    private final ReservationService reservationService;

    public ReservationResource(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * POST  /reservations : Create a new reservation.
     *
     * @param reservationDTO the reservationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reservationDTO, or with status 400 (Bad Request) if the reservation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservationDTO);
        if (reservationDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationDTO result = reservationService.save(reservationDTO);
        return ResponseEntity.created(new URI("/api/reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reservations : Updates an existing reservation.
     *
     * @param reservationDTO the reservationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reservationDTO,
     * or with status 400 (Bad Request) if the reservationDTO is not valid,
     * or with status 500 (Internal Server Error) if the reservationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reservations")
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}", reservationDTO);
        if (reservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReservationDTO result = reservationService.save(reservationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reservationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reservations : get all the reservations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reservations in body
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(Pageable pageable) {
        log.debug("REST request to get a page of Reservations");
        Page<ReservationDTO> page = reservationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reservations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /reservations/:id : get the "id" reservation.
     *
     * @param id the id of the reservationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reservationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Optional<ReservationDTO> reservationDTO = reservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservationDTO);
    }

    /**
     * DELETE  /reservations/:id : delete the "id" reservation.
     *
     * @param id the id of the reservationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
