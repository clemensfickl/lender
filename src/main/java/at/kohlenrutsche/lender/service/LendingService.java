package at.kohlenrutsche.lender.service;

import at.kohlenrutsche.lender.service.dto.LendingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Lending.
 */
public interface LendingService {

    /**
     * Save a lending.
     *
     * @param lendingDTO the entity to save
     * @return the persisted entity
     */
    LendingDTO save(LendingDTO lendingDTO);

    /**
     * Get all the lendings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LendingDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lending.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LendingDTO> findOne(Long id);

    /**
     * Delete the "id" lending.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
