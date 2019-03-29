package at.kohlenrutsche.lender.web.rest;
import at.kohlenrutsche.lender.service.ItemCostService;
import at.kohlenrutsche.lender.web.rest.errors.BadRequestAlertException;
import at.kohlenrutsche.lender.web.rest.util.HeaderUtil;
import at.kohlenrutsche.lender.web.rest.util.PaginationUtil;
import at.kohlenrutsche.lender.service.dto.ItemCostDTO;
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
 * REST controller for managing ItemCost.
 */
@RestController
@RequestMapping("/api")
public class ItemCostResource {

    private final Logger log = LoggerFactory.getLogger(ItemCostResource.class);

    private static final String ENTITY_NAME = "itemCost";

    private final ItemCostService itemCostService;

    public ItemCostResource(ItemCostService itemCostService) {
        this.itemCostService = itemCostService;
    }

    /**
     * POST  /item-costs : Create a new itemCost.
     *
     * @param itemCostDTO the itemCostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemCostDTO, or with status 400 (Bad Request) if the itemCost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-costs")
    public ResponseEntity<ItemCostDTO> createItemCost(@Valid @RequestBody ItemCostDTO itemCostDTO) throws URISyntaxException {
        log.debug("REST request to save ItemCost : {}", itemCostDTO);
        if (itemCostDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemCost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCostDTO result = itemCostService.save(itemCostDTO);
        return ResponseEntity.created(new URI("/api/item-costs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-costs : Updates an existing itemCost.
     *
     * @param itemCostDTO the itemCostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemCostDTO,
     * or with status 400 (Bad Request) if the itemCostDTO is not valid,
     * or with status 500 (Internal Server Error) if the itemCostDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-costs")
    public ResponseEntity<ItemCostDTO> updateItemCost(@Valid @RequestBody ItemCostDTO itemCostDTO) throws URISyntaxException {
        log.debug("REST request to update ItemCost : {}", itemCostDTO);
        if (itemCostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCostDTO result = itemCostService.save(itemCostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemCostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-costs : get all the itemCosts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of itemCosts in body
     */
    @GetMapping("/item-costs")
    public ResponseEntity<List<ItemCostDTO>> getAllItemCosts(Pageable pageable) {
        log.debug("REST request to get a page of ItemCosts");
        Page<ItemCostDTO> page = itemCostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/item-costs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /item-costs/:id : get the "id" itemCost.
     *
     * @param id the id of the itemCostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemCostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/item-costs/{id}")
    public ResponseEntity<ItemCostDTO> getItemCost(@PathVariable Long id) {
        log.debug("REST request to get ItemCost : {}", id);
        Optional<ItemCostDTO> itemCostDTO = itemCostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemCostDTO);
    }

    /**
     * DELETE  /item-costs/:id : delete the "id" itemCost.
     *
     * @param id the id of the itemCostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-costs/{id}")
    public ResponseEntity<Void> deleteItemCost(@PathVariable Long id) {
        log.debug("REST request to delete ItemCost : {}", id);
        itemCostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
