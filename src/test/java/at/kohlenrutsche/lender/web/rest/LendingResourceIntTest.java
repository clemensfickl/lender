package at.kohlenrutsche.lender.web.rest;

import at.kohlenrutsche.lender.LenderApp;

import at.kohlenrutsche.lender.domain.Lending;
import at.kohlenrutsche.lender.domain.User;
import at.kohlenrutsche.lender.domain.Item;
import at.kohlenrutsche.lender.repository.LendingRepository;
import at.kohlenrutsche.lender.service.LendingService;
import at.kohlenrutsche.lender.service.dto.LendingDTO;
import at.kohlenrutsche.lender.service.mapper.LendingMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static at.kohlenrutsche.lender.web.rest.TestUtil.sameInstant;
import static at.kohlenrutsche.lender.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LendingResource REST controller.
 *
 * @see LendingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenderApp.class)
public class LendingResourceIntTest {

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_INFORMED_ABOUT_END = false;
    private static final Boolean UPDATED_INFORMED_ABOUT_END = true;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    @Autowired
    private LendingRepository lendingRepository;

    @Autowired
    private LendingMapper lendingMapper;

    @Autowired
    private LendingService lendingService;

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

    private MockMvc restLendingMockMvc;

    private Lending lending;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LendingResource lendingResource = new LendingResource(lendingService);
        this.restLendingMockMvc = MockMvcBuilders.standaloneSetup(lendingResource)
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
    public static Lending createEntity(EntityManager em) {
        Lending lending = new Lending()
            .start(DEFAULT_START)
            .plannedEnd(DEFAULT_PLANNED_END)
            .end(DEFAULT_END)
            .informedAboutEnd(DEFAULT_INFORMED_ABOUT_END)
            .cost(DEFAULT_COST)
            .paid(DEFAULT_PAID);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        lending.setBorrower(user);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        lending.setItem(item);
        return lending;
    }

    @Before
    public void initTest() {
        lending = createEntity(em);
    }

    @Test
    @Transactional
    public void createLending() throws Exception {
        int databaseSizeBeforeCreate = lendingRepository.findAll().size();

        // Create the Lending
        LendingDTO lendingDTO = lendingMapper.toDto(lending);
        restLendingMockMvc.perform(post("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isCreated());

        // Validate the Lending in the database
        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeCreate + 1);
        Lending testLending = lendingList.get(lendingList.size() - 1);
        assertThat(testLending.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testLending.getPlannedEnd()).isEqualTo(DEFAULT_PLANNED_END);
        assertThat(testLending.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testLending.isInformedAboutEnd()).isEqualTo(DEFAULT_INFORMED_ABOUT_END);
        assertThat(testLending.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testLending.isPaid()).isEqualTo(DEFAULT_PAID);
    }

    @Test
    @Transactional
    public void createLendingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lendingRepository.findAll().size();

        // Create the Lending with an existing ID
        lending.setId(1L);
        LendingDTO lendingDTO = lendingMapper.toDto(lending);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLendingMockMvc.perform(post("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lending in the database
        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = lendingRepository.findAll().size();
        // set the field null
        lending.setStart(null);

        // Create the Lending, which fails.
        LendingDTO lendingDTO = lendingMapper.toDto(lending);

        restLendingMockMvc.perform(post("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isBadRequest());

        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlannedEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = lendingRepository.findAll().size();
        // set the field null
        lending.setPlannedEnd(null);

        // Create the Lending, which fails.
        LendingDTO lendingDTO = lendingMapper.toDto(lending);

        restLendingMockMvc.perform(post("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isBadRequest());

        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLendings() throws Exception {
        // Initialize the database
        lendingRepository.saveAndFlush(lending);

        // Get all the lendingList
        restLendingMockMvc.perform(get("/api/lendings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lending.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].plannedEnd").value(hasItem(sameInstant(DEFAULT_PLANNED_END))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))))
            .andExpect(jsonPath("$.[*].informedAboutEnd").value(hasItem(DEFAULT_INFORMED_ABOUT_END.booleanValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLending() throws Exception {
        // Initialize the database
        lendingRepository.saveAndFlush(lending);

        // Get the lending
        restLendingMockMvc.perform(get("/api/lendings/{id}", lending.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lending.getId().intValue()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.plannedEnd").value(sameInstant(DEFAULT_PLANNED_END)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)))
            .andExpect(jsonPath("$.informedAboutEnd").value(DEFAULT_INFORMED_ABOUT_END.booleanValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLending() throws Exception {
        // Get the lending
        restLendingMockMvc.perform(get("/api/lendings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLending() throws Exception {
        // Initialize the database
        lendingRepository.saveAndFlush(lending);

        int databaseSizeBeforeUpdate = lendingRepository.findAll().size();

        // Update the lending
        Lending updatedLending = lendingRepository.findById(lending.getId()).get();
        // Disconnect from session so that the updates on updatedLending are not directly saved in db
        em.detach(updatedLending);
        updatedLending
            .start(UPDATED_START)
            .plannedEnd(UPDATED_PLANNED_END)
            .end(UPDATED_END)
            .informedAboutEnd(UPDATED_INFORMED_ABOUT_END)
            .cost(UPDATED_COST)
            .paid(UPDATED_PAID);
        LendingDTO lendingDTO = lendingMapper.toDto(updatedLending);

        restLendingMockMvc.perform(put("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isOk());

        // Validate the Lending in the database
        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeUpdate);
        Lending testLending = lendingList.get(lendingList.size() - 1);
        assertThat(testLending.getStart()).isEqualTo(UPDATED_START);
        assertThat(testLending.getPlannedEnd()).isEqualTo(UPDATED_PLANNED_END);
        assertThat(testLending.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testLending.isInformedAboutEnd()).isEqualTo(UPDATED_INFORMED_ABOUT_END);
        assertThat(testLending.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testLending.isPaid()).isEqualTo(UPDATED_PAID);
    }

    @Test
    @Transactional
    public void updateNonExistingLending() throws Exception {
        int databaseSizeBeforeUpdate = lendingRepository.findAll().size();

        // Create the Lending
        LendingDTO lendingDTO = lendingMapper.toDto(lending);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLendingMockMvc.perform(put("/api/lendings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lendingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lending in the database
        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLending() throws Exception {
        // Initialize the database
        lendingRepository.saveAndFlush(lending);

        int databaseSizeBeforeDelete = lendingRepository.findAll().size();

        // Delete the lending
        restLendingMockMvc.perform(delete("/api/lendings/{id}", lending.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lending> lendingList = lendingRepository.findAll();
        assertThat(lendingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lending.class);
        Lending lending1 = new Lending();
        lending1.setId(1L);
        Lending lending2 = new Lending();
        lending2.setId(lending1.getId());
        assertThat(lending1).isEqualTo(lending2);
        lending2.setId(2L);
        assertThat(lending1).isNotEqualTo(lending2);
        lending1.setId(null);
        assertThat(lending1).isNotEqualTo(lending2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LendingDTO.class);
        LendingDTO lendingDTO1 = new LendingDTO();
        lendingDTO1.setId(1L);
        LendingDTO lendingDTO2 = new LendingDTO();
        assertThat(lendingDTO1).isNotEqualTo(lendingDTO2);
        lendingDTO2.setId(lendingDTO1.getId());
        assertThat(lendingDTO1).isEqualTo(lendingDTO2);
        lendingDTO2.setId(2L);
        assertThat(lendingDTO1).isNotEqualTo(lendingDTO2);
        lendingDTO1.setId(null);
        assertThat(lendingDTO1).isNotEqualTo(lendingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lendingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lendingMapper.fromId(null)).isNull();
    }
}
