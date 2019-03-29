package at.kohlenrutsche.lender.service.impl;

import at.kohlenrutsche.lender.service.LendingService;
import at.kohlenrutsche.lender.domain.Lending;
import at.kohlenrutsche.lender.repository.LendingRepository;
import at.kohlenrutsche.lender.service.dto.LendingDTO;
import at.kohlenrutsche.lender.service.mapper.LendingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Lending.
 */
@Service
@Transactional
public class LendingServiceImpl implements LendingService {

    private final Logger log = LoggerFactory.getLogger(LendingServiceImpl.class);

    private final LendingRepository lendingRepository;

    private final LendingMapper lendingMapper;

    public LendingServiceImpl(LendingRepository lendingRepository, LendingMapper lendingMapper) {
        this.lendingRepository = lendingRepository;
        this.lendingMapper = lendingMapper;
    }

    /**
     * Save a lending.
     *
     * @param lendingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LendingDTO save(LendingDTO lendingDTO) {
        log.debug("Request to save Lending : {}", lendingDTO);
        Lending lending = lendingMapper.toEntity(lendingDTO);
        lending = lendingRepository.save(lending);
        return lendingMapper.toDto(lending);
    }

    /**
     * Get all the lendings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LendingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lendings");
        return lendingRepository.findAll(pageable)
            .map(lendingMapper::toDto);
    }


    /**
     * Get one lending by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LendingDTO> findOne(Long id) {
        log.debug("Request to get Lending : {}", id);
        return lendingRepository.findById(id)
            .map(lendingMapper::toDto);
    }

    /**
     * Delete the lending by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lending : {}", id);
        lendingRepository.deleteById(id);
    }
}
