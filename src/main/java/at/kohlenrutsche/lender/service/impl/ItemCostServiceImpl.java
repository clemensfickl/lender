package at.kohlenrutsche.lender.service.impl;

import at.kohlenrutsche.lender.service.ItemCostService;
import at.kohlenrutsche.lender.domain.ItemCost;
import at.kohlenrutsche.lender.repository.ItemCostRepository;
import at.kohlenrutsche.lender.service.dto.ItemCostDTO;
import at.kohlenrutsche.lender.service.mapper.ItemCostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ItemCost.
 */
@Service
@Transactional
public class ItemCostServiceImpl implements ItemCostService {

    private final Logger log = LoggerFactory.getLogger(ItemCostServiceImpl.class);

    private final ItemCostRepository itemCostRepository;

    private final ItemCostMapper itemCostMapper;

    public ItemCostServiceImpl(ItemCostRepository itemCostRepository, ItemCostMapper itemCostMapper) {
        this.itemCostRepository = itemCostRepository;
        this.itemCostMapper = itemCostMapper;
    }

    /**
     * Save a itemCost.
     *
     * @param itemCostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemCostDTO save(ItemCostDTO itemCostDTO) {
        log.debug("Request to save ItemCost : {}", itemCostDTO);
        ItemCost itemCost = itemCostMapper.toEntity(itemCostDTO);
        itemCost = itemCostRepository.save(itemCost);
        return itemCostMapper.toDto(itemCost);
    }

    /**
     * Get all the itemCosts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemCostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ItemCosts");
        return itemCostRepository.findAll(pageable)
            .map(itemCostMapper::toDto);
    }


    /**
     * Get one itemCost by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemCostDTO> findOne(Long id) {
        log.debug("Request to get ItemCost : {}", id);
        return itemCostRepository.findById(id)
            .map(itemCostMapper::toDto);
    }

    /**
     * Delete the itemCost by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemCost : {}", id);
        itemCostRepository.deleteById(id);
    }
}
