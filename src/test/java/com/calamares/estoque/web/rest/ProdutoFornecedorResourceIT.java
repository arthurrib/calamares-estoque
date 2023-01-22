package com.calamares.estoque.web.rest;

import static com.calamares.estoque.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.calamares.estoque.IntegrationTest;
import com.calamares.estoque.domain.ProdutoFornecedor;
import com.calamares.estoque.repository.ProdutoFornecedorRepository;
import com.calamares.estoque.service.dto.ProdutoFornecedorDTO;
import com.calamares.estoque.service.mapper.ProdutoFornecedorMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProdutoFornecedorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdutoFornecedorResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/produto-fornecedors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProdutoFornecedorRepository produtoFornecedorRepository;

    @Autowired
    private ProdutoFornecedorMapper produtoFornecedorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdutoFornecedorMockMvc;

    private ProdutoFornecedor produtoFornecedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdutoFornecedor createEntity(EntityManager em) {
        ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor().valor(DEFAULT_VALOR).observacoes(DEFAULT_OBSERVACOES);
        return produtoFornecedor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdutoFornecedor createUpdatedEntity(EntityManager em) {
        ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor().valor(UPDATED_VALOR).observacoes(UPDATED_OBSERVACOES);
        return produtoFornecedor;
    }

    @BeforeEach
    public void initTest() {
        produtoFornecedor = createEntity(em);
    }

    @Test
    @Transactional
    void createProdutoFornecedor() throws Exception {
        int databaseSizeBeforeCreate = produtoFornecedorRepository.findAll().size();
        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);
        restProdutoFornecedorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        ProdutoFornecedor testProdutoFornecedor = produtoFornecedorList.get(produtoFornecedorList.size() - 1);
        assertThat(testProdutoFornecedor.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
        assertThat(testProdutoFornecedor.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    void createProdutoFornecedorWithExistingId() throws Exception {
        // Create the ProdutoFornecedor with an existing ID
        produtoFornecedor.setId(1L);
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        int databaseSizeBeforeCreate = produtoFornecedorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoFornecedorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoFornecedorRepository.findAll().size();
        // set the field null
        produtoFornecedor.setValor(null);

        // Create the ProdutoFornecedor, which fails.
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        restProdutoFornecedorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProdutoFornecedors() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        // Get all the produtoFornecedorList
        restProdutoFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoFornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)));
    }

    @Test
    @Transactional
    void getProdutoFornecedor() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        // Get the produtoFornecedor
        restProdutoFornecedorMockMvc
            .perform(get(ENTITY_API_URL_ID, produtoFornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produtoFornecedor.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES));
    }

    @Test
    @Transactional
    void getNonExistingProdutoFornecedor() throws Exception {
        // Get the produtoFornecedor
        restProdutoFornecedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProdutoFornecedor() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();

        // Update the produtoFornecedor
        ProdutoFornecedor updatedProdutoFornecedor = produtoFornecedorRepository.findById(produtoFornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedProdutoFornecedor are not directly saved in db
        em.detach(updatedProdutoFornecedor);
        updatedProdutoFornecedor.valor(UPDATED_VALOR).observacoes(UPDATED_OBSERVACOES);
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(updatedProdutoFornecedor);

        restProdutoFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produtoFornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
        ProdutoFornecedor testProdutoFornecedor = produtoFornecedorList.get(produtoFornecedorList.size() - 1);
        assertThat(testProdutoFornecedor.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testProdutoFornecedor.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void putNonExistingProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produtoFornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdutoFornecedorWithPatch() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();

        // Update the produtoFornecedor using partial update
        ProdutoFornecedor partialUpdatedProdutoFornecedor = new ProdutoFornecedor();
        partialUpdatedProdutoFornecedor.setId(produtoFornecedor.getId());

        partialUpdatedProdutoFornecedor.valor(UPDATED_VALOR);

        restProdutoFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdutoFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdutoFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
        ProdutoFornecedor testProdutoFornecedor = produtoFornecedorList.get(produtoFornecedorList.size() - 1);
        assertThat(testProdutoFornecedor.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testProdutoFornecedor.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
    }

    @Test
    @Transactional
    void fullUpdateProdutoFornecedorWithPatch() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();

        // Update the produtoFornecedor using partial update
        ProdutoFornecedor partialUpdatedProdutoFornecedor = new ProdutoFornecedor();
        partialUpdatedProdutoFornecedor.setId(produtoFornecedor.getId());

        partialUpdatedProdutoFornecedor.valor(UPDATED_VALOR).observacoes(UPDATED_OBSERVACOES);

        restProdutoFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdutoFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdutoFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
        ProdutoFornecedor testProdutoFornecedor = produtoFornecedorList.get(produtoFornecedorList.size() - 1);
        assertThat(testProdutoFornecedor.getValor()).isEqualByComparingTo(UPDATED_VALOR);
        assertThat(testProdutoFornecedor.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void patchNonExistingProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produtoFornecedorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdutoFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = produtoFornecedorRepository.findAll().size();
        produtoFornecedor.setId(count.incrementAndGet());

        // Create the ProdutoFornecedor
        ProdutoFornecedorDTO produtoFornecedorDTO = produtoFornecedorMapper.toDto(produtoFornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdutoFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produtoFornecedorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProdutoFornecedor in the database
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdutoFornecedor() throws Exception {
        // Initialize the database
        produtoFornecedorRepository.saveAndFlush(produtoFornecedor);

        int databaseSizeBeforeDelete = produtoFornecedorRepository.findAll().size();

        // Delete the produtoFornecedor
        restProdutoFornecedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, produtoFornecedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdutoFornecedor> produtoFornecedorList = produtoFornecedorRepository.findAll();
        assertThat(produtoFornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
