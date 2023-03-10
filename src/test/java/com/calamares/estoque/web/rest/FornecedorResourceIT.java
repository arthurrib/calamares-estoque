package com.calamares.estoque.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.calamares.estoque.IntegrationTest;
import com.calamares.estoque.domain.Fornecedor;
import com.calamares.estoque.repository.FornecedorRepository;
import com.calamares.estoque.service.dto.FornecedorDTO;
import com.calamares.estoque.service.mapper.FornecedorMapper;
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
 * Integration tests for the {@link FornecedorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fornecedors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor().nome(DEFAULT_NOME).telefone(DEFAULT_TELEFONE);
        return fornecedor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornecedor createUpdatedEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor().nome(UPDATED_NOME).telefone(UPDATED_TELEFONE);
        return fornecedor;
    }

    @BeforeEach
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();
        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);
        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createFornecedorWithExistingId() throws Exception {
        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNome(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setTelefone(null);

        // Create the Fornecedor, which fails.
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        restFornecedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(updatedFornecedor);

        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateFornecedorWithPatch() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor using partial update
        Fornecedor partialUpdatedFornecedor = new Fornecedor();
        partialUpdatedFornecedor.setId(fornecedor.getId());

        partialUpdatedFornecedor.nome(UPDATED_NOME).telefone(UPDATED_TELEFONE);

        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedor))
            )
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();
        fornecedor.setId(count.incrementAndGet());

        // Create the Fornecedor
        FornecedorDTO fornecedorDTO = fornecedorMapper.toDto(fornecedor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fornecedorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
