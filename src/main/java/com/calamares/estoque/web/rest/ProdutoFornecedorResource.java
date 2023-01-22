package com.calamares.estoque.web.rest;

import com.calamares.estoque.repository.ProdutoFornecedorRepository;
import com.calamares.estoque.service.ProdutoFornecedorService;
import com.calamares.estoque.service.dto.ProdutoFornecedorDTO;
import com.calamares.estoque.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.calamares.estoque.domain.ProdutoFornecedor}.
 */
@RestController
@RequestMapping("/api")
public class ProdutoFornecedorResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoFornecedorResource.class);

    private static final String ENTITY_NAME = "produtoFornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdutoFornecedorService produtoFornecedorService;

    private final ProdutoFornecedorRepository produtoFornecedorRepository;

    public ProdutoFornecedorResource(
        ProdutoFornecedorService produtoFornecedorService,
        ProdutoFornecedorRepository produtoFornecedorRepository
    ) {
        this.produtoFornecedorService = produtoFornecedorService;
        this.produtoFornecedorRepository = produtoFornecedorRepository;
    }

    /**
     * {@code POST  /produto-fornecedors} : Create a new produtoFornecedor.
     *
     * @param produtoFornecedorDTO the produtoFornecedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produtoFornecedorDTO, or with status {@code 400 (Bad Request)} if the produtoFornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produto-fornecedors")
    public ResponseEntity<ProdutoFornecedorDTO> createProdutoFornecedor(@Valid @RequestBody ProdutoFornecedorDTO produtoFornecedorDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProdutoFornecedor : {}", produtoFornecedorDTO);
        if (produtoFornecedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new produtoFornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutoFornecedorDTO result = produtoFornecedorService.save(produtoFornecedorDTO);
        return ResponseEntity
            .created(new URI("/api/produto-fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produto-fornecedors/:id} : Updates an existing produtoFornecedor.
     *
     * @param id the id of the produtoFornecedorDTO to save.
     * @param produtoFornecedorDTO the produtoFornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produtoFornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the produtoFornecedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produtoFornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produto-fornecedors/{id}")
    public ResponseEntity<ProdutoFornecedorDTO> updateProdutoFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProdutoFornecedorDTO produtoFornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProdutoFornecedor : {}, {}", id, produtoFornecedorDTO);
        if (produtoFornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produtoFornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produtoFornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProdutoFornecedorDTO result = produtoFornecedorService.update(produtoFornecedorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produtoFornecedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produto-fornecedors/:id} : Partial updates given fields of an existing produtoFornecedor, field will ignore if it is null
     *
     * @param id the id of the produtoFornecedorDTO to save.
     * @param produtoFornecedorDTO the produtoFornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produtoFornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the produtoFornecedorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produtoFornecedorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produtoFornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/produto-fornecedors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProdutoFornecedorDTO> partialUpdateProdutoFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProdutoFornecedorDTO produtoFornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProdutoFornecedor partially : {}, {}", id, produtoFornecedorDTO);
        if (produtoFornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produtoFornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produtoFornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProdutoFornecedorDTO> result = produtoFornecedorService.partialUpdate(produtoFornecedorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produtoFornecedorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produto-fornecedors} : get all the produtoFornecedors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produtoFornecedors in body.
     */
    @GetMapping("/produto-fornecedors")
    public List<ProdutoFornecedorDTO> getAllProdutoFornecedors() {
        log.debug("REST request to get all ProdutoFornecedors");
        return produtoFornecedorService.findAll();
    }

    /**
     * {@code GET  /produto-fornecedors/:id} : get the "id" produtoFornecedor.
     *
     * @param id the id of the produtoFornecedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produtoFornecedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produto-fornecedors/{id}")
    public ResponseEntity<ProdutoFornecedorDTO> getProdutoFornecedor(@PathVariable Long id) {
        log.debug("REST request to get ProdutoFornecedor : {}", id);
        Optional<ProdutoFornecedorDTO> produtoFornecedorDTO = produtoFornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produtoFornecedorDTO);
    }

    /**
     * {@code DELETE  /produto-fornecedors/:id} : delete the "id" produtoFornecedor.
     *
     * @param id the id of the produtoFornecedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produto-fornecedors/{id}")
    public ResponseEntity<Void> deleteProdutoFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete ProdutoFornecedor : {}", id);
        produtoFornecedorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
