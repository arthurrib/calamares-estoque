package com.calamares.estoque.service;

import com.calamares.estoque.domain.ProdutoFornecedor;
import com.calamares.estoque.repository.ProdutoFornecedorRepository;
import com.calamares.estoque.service.dto.ProdutoFornecedorDTO;
import com.calamares.estoque.service.mapper.ProdutoFornecedorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProdutoFornecedor}.
 */
@Service
@Transactional
public class ProdutoFornecedorService {

    private final Logger log = LoggerFactory.getLogger(ProdutoFornecedorService.class);

    private final ProdutoFornecedorRepository produtoFornecedorRepository;

    private final ProdutoFornecedorMapper produtoFornecedorMapper;

    public ProdutoFornecedorService(
        ProdutoFornecedorRepository produtoFornecedorRepository,
        ProdutoFornecedorMapper produtoFornecedorMapper
    ) {
        this.produtoFornecedorRepository = produtoFornecedorRepository;
        this.produtoFornecedorMapper = produtoFornecedorMapper;
    }

    /**
     * Save a produtoFornecedor.
     *
     * @param produtoFornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdutoFornecedorDTO save(ProdutoFornecedorDTO produtoFornecedorDTO) {
        log.debug("Request to save ProdutoFornecedor : {}", produtoFornecedorDTO);
        ProdutoFornecedor produtoFornecedor = produtoFornecedorMapper.toEntity(produtoFornecedorDTO);
        produtoFornecedor = produtoFornecedorRepository.save(produtoFornecedor);
        return produtoFornecedorMapper.toDto(produtoFornecedor);
    }

    /**
     * Update a produtoFornecedor.
     *
     * @param produtoFornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProdutoFornecedorDTO update(ProdutoFornecedorDTO produtoFornecedorDTO) {
        log.debug("Request to update ProdutoFornecedor : {}", produtoFornecedorDTO);
        ProdutoFornecedor produtoFornecedor = produtoFornecedorMapper.toEntity(produtoFornecedorDTO);
        produtoFornecedor = produtoFornecedorRepository.save(produtoFornecedor);
        return produtoFornecedorMapper.toDto(produtoFornecedor);
    }

    /**
     * Partially update a produtoFornecedor.
     *
     * @param produtoFornecedorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProdutoFornecedorDTO> partialUpdate(ProdutoFornecedorDTO produtoFornecedorDTO) {
        log.debug("Request to partially update ProdutoFornecedor : {}", produtoFornecedorDTO);

        return produtoFornecedorRepository
            .findById(produtoFornecedorDTO.getId())
            .map(existingProdutoFornecedor -> {
                produtoFornecedorMapper.partialUpdate(existingProdutoFornecedor, produtoFornecedorDTO);

                return existingProdutoFornecedor;
            })
            .map(produtoFornecedorRepository::save)
            .map(produtoFornecedorMapper::toDto);
    }

    /**
     * Get all the produtoFornecedors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProdutoFornecedorDTO> findAll() {
        log.debug("Request to get all ProdutoFornecedors");
        return produtoFornecedorRepository
            .findAll()
            .stream()
            .map(produtoFornecedorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one produtoFornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProdutoFornecedorDTO> findOne(Long id) {
        log.debug("Request to get ProdutoFornecedor : {}", id);
        return produtoFornecedorRepository.findById(id).map(produtoFornecedorMapper::toDto);
    }

    /**
     * Delete the produtoFornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProdutoFornecedor : {}", id);
        produtoFornecedorRepository.deleteById(id);
    }
}
