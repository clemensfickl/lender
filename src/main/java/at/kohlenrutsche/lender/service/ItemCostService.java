package at.kohlenrutsche.lender.service;

import at.kohlenrutsche.lender.service.dto.ItemCostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ItemCost.
 */
public interface ItemCostService {

    /**
     * Save a itemCost.
     *
     * @param itemCostDTO the entity to save
     * @return the persisted entity
     */
    ItemCostDTO save(ItemCostDTO itemCostDTO);

    /**
     * Get all the itemCosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ItemCostDTO> findAll(Pageable pageable);


    /**
     * Get the "id" itemCost.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ItemCostDTO> findOne(Long id);

    /**
     * Delete the "id" itemCost.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
