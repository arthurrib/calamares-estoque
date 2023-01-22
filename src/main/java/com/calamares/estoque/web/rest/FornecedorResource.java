package com.calamares.estoque.web.rest;

import com.calamares.estoque.repository.FornecedorRepository;
import com.calamares.estoque.service.FornecedorService;
import com.calamares.estoque.service.dto.FornecedorDTO;
import com.calamares.estoque.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.calamares.estoque.domain.Fornecedor}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorService fornecedorService;

    private final FornecedorRepository fornecedorRepository;

    public FornecedorResource(FornecedorService fornecedorService, FornecedorRepository fornecedorRepository) {
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;
    }

    /**
     * {@code POST  /fornecedors} : Create a new fornecedor.
     *
     * @param fornecedorDTO the fornecedorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorDTO, or with status {@code 400 (Bad Request)} if the fornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fornecedors")
    public ResponseEntity<FornecedorDTO> createFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedorDTO);
        if (fornecedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorDTO result = fornecedorService.save(fornecedorDTO);
        return ResponseEntity
            .created(new URI("/api/fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fornecedors/:id} : Updates an existing fornecedor.
     *
     * @param id the id of the fornecedorDTO to save.
     * @param fornecedorDTO the fornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fornecedors/{id}")
    public ResponseEntity<FornecedorDTO> updateFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorDTO fornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}, {}", id, fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FornecedorDTO result = fornecedorService.update(fornecedorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fornecedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fornecedors/:id} : Partial updates given fields of an existing fornecedor, field will ignore if it is null
     *
     * @param id the id of the fornecedorDTO to save.
     * @param fornecedorDTO the fornecedorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fornecedors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorDTO> partialUpdateFornecedor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorDTO fornecedorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fornecedor partially : {}, {}", id, fornecedorDTO);
        if (fornecedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorDTO> result = fornecedorService.partialUpdate(fornecedorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fornecedorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedors} : get all the fornecedors.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedors in body.
     */
    @GetMapping("/fornecedors")
    public ResponseEntity<List<FornecedorDTO>> getAllFornecedors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("produtofornecedor-is-null".equals(filter)) {
            log.debug("REST request to get all Fornecedors where produtoFornecedor is null");
            return new ResponseEntity<>(fornecedorService.findAllWhereProdutoFornecedorIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Fornecedors");
        Page<FornecedorDTO> page = fornecedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fornecedors/:id} : get the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fornecedors/{id}")
    public ResponseEntity<FornecedorDTO> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<FornecedorDTO> fornecedorDTO = fornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorDTO);
    }

    /**
     * {@code DELETE  /fornecedors/:id} : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fornecedors/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
