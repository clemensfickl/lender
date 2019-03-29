package at.kohlenrutsche.lender.web.rest;

import at.kohlenrutsche.lender.LenderApp;

import at.kohlenrutsche.lender.domain.ItemCost;
import at.kohlenrutsche.lender.domain.Item;
import at.kohlenrutsche.lender.repository.ItemCostRepository;
import at.kohlenrutsche.lender.service.ItemCostService;
import at.kohlenrutsche.lender.service.dto.ItemCostDTO;
import at.kohlenrutsche.lender.service.mapper.ItemCostMapper;
import at.kohlenrutsche.lender.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;


import static at.kohlenrutsche.lender.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import at.kohlenrutsche.lender.domain.enumeration.TimeFrame;
/**
 * Test class for the ItemCostResource REST controller.
 *
 * @see ItemCostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenderApp.class)
public class ItemCostResourceIntTest {

    private static final TimeFrame DEFAULT_TIME_FRAME = TimeFrame.MINUTE;
    private static final TimeFrame UPDATED_TIME_FRAME = TimeFrame.HOUR;

    private static final BigDecimal DEFAULT_COST_PER_TIME_FRAME = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_PER_TIME_FRAME = new BigDecimal(2);

    @Autowired
    private ItemCostRepository itemCostRepository;

    @Autowired
    private ItemCostMapper itemCostMapper;

    @Autowired
    private ItemCostService itemCostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restItemCostMockMvc;

    private ItemCost itemCost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemCostResource itemCostResource = new ItemCostResource(itemCostService);
        this.restItemCostMockMvc = MockMvcBuilders.standaloneSetup(itemCostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCost createEntity(EntityManager em) {
        ItemCost itemCost = new ItemCost()
            .timeFrame(DEFAULT_TIME_FRAME)
            .costPerTimeFrame(DEFAULT_COST_PER_TIME_FRAME);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        itemCost.setItem(item);
        return itemCost;
    }

    @Before
    public void initTest() {
        itemCost = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemCost() throws Exception {
        int databaseSizeBeforeCreate = itemCostRepository.findAll().size();

        // Create the ItemCost
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(itemCost);
        restItemCostMockMvc.perform(post("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isCreated());

        // Validate the ItemCost in the database
        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCost testItemCost = itemCostList.get(itemCostList.size() - 1);
        assertThat(testItemCost.getTimeFrame()).isEqualTo(DEFAULT_TIME_FRAME);
        assertThat(testItemCost.getCostPerTimeFrame()).isEqualTo(DEFAULT_COST_PER_TIME_FRAME);
    }

    @Test
    @Transactional
    public void createItemCostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemCostRepository.findAll().size();

        // Create the ItemCost with an existing ID
        itemCost.setId(1L);
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(itemCost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCostMockMvc.perform(post("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCost in the database
        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTimeFrameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCostRepository.findAll().size();
        // set the field null
        itemCost.setTimeFrame(null);

        // Create the ItemCost, which fails.
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(itemCost);

        restItemCostMockMvc.perform(post("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isBadRequest());

        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCostPerTimeFrameIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCostRepository.findAll().size();
        // set the field null
        itemCost.setCostPerTimeFrame(null);

        // Create the ItemCost, which fails.
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(itemCost);

        restItemCostMockMvc.perform(post("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isBadRequest());

        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemCosts() throws Exception {
        // Initialize the database
        itemCostRepository.saveAndFlush(itemCost);

        // Get all the itemCostList
        restItemCostMockMvc.perform(get("/api/item-costs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCost.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeFrame").value(hasItem(DEFAULT_TIME_FRAME.toString())))
            .andExpect(jsonPath("$.[*].costPerTimeFrame").value(hasItem(DEFAULT_COST_PER_TIME_FRAME.intValue())));
    }
    
    @Test
    @Transactional
    public void getItemCost() throws Exception {
        // Initialize the database
        itemCostRepository.saveAndFlush(itemCost);

        // Get the itemCost
        restItemCostMockMvc.perform(get("/api/item-costs/{id}", itemCost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemCost.getId().intValue()))
            .andExpect(jsonPath("$.timeFrame").value(DEFAULT_TIME_FRAME.toString()))
            .andExpect(jsonPath("$.costPerTimeFrame").value(DEFAULT_COST_PER_TIME_FRAME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemCost() throws Exception {
        // Get the itemCost
        restItemCostMockMvc.perform(get("/api/item-costs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemCost() throws Exception {
        // Initialize the database
        itemCostRepository.saveAndFlush(itemCost);

        int databaseSizeBeforeUpdate = itemCostRepository.findAll().size();

        // Update the itemCost
        ItemCost updatedItemCost = itemCostRepository.findById(itemCost.getId()).get();
        // Disconnect from session so that the updates on updatedItemCost are not directly saved in db
        em.detach(updatedItemCost);
        updatedItemCost
            .timeFrame(UPDATED_TIME_FRAME)
            .costPerTimeFrame(UPDATED_COST_PER_TIME_FRAME);
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(updatedItemCost);

        restItemCostMockMvc.perform(put("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isOk());

        // Validate the ItemCost in the database
        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeUpdate);
        ItemCost testItemCost = itemCostList.get(itemCostList.size() - 1);
        assertThat(testItemCost.getTimeFrame()).isEqualTo(UPDATED_TIME_FRAME);
        assertThat(testItemCost.getCostPerTimeFrame()).isEqualTo(UPDATED_COST_PER_TIME_FRAME);
    }

    @Test
    @Transactional
    public void updateNonExistingItemCost() throws Exception {
        int databaseSizeBeforeUpdate = itemCostRepository.findAll().size();

        // Create the ItemCost
        ItemCostDTO itemCostDTO = itemCostMapper.toDto(itemCost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCostMockMvc.perform(put("/api/item-costs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCost in the database
        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemCost() throws Exception {
        // Initialize the database
        itemCostRepository.saveAndFlush(itemCost);

        int databaseSizeBeforeDelete = itemCostRepository.findAll().size();

        // Delete the itemCost
        restItemCostMockMvc.perform(delete("/api/item-costs/{id}", itemCost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemCost> itemCostList = itemCostRepository.findAll();
        assertThat(itemCostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCost.class);
        ItemCost itemCost1 = new ItemCost();
        itemCost1.setId(1L);
        ItemCost itemCost2 = new ItemCost();
        itemCost2.setId(itemCost1.getId());
        assertThat(itemCost1).isEqualTo(itemCost2);
        itemCost2.setId(2L);
        assertThat(itemCost1).isNotEqualTo(itemCost2);
        itemCost1.setId(null);
        assertThat(itemCost1).isNotEqualTo(itemCost2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCostDTO.class);
        ItemCostDTO itemCostDTO1 = new ItemCostDTO();
        itemCostDTO1.setId(1L);
        ItemCostDTO itemCostDTO2 = new ItemCostDTO();
        assertThat(itemCostDTO1).isNotEqualTo(itemCostDTO2);
        itemCostDTO2.setId(itemCostDTO1.getId());
        assertThat(itemCostDTO1).isEqualTo(itemCostDTO2);
        itemCostDTO2.setId(2L);
        assertThat(itemCostDTO1).isNotEqualTo(itemCostDTO2);
        itemCostDTO1.setId(null);
        assertThat(itemCostDTO1).isNotEqualTo(itemCostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(itemCostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(itemCostMapper.fromId(null)).isNull();
    }
}
