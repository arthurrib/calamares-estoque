package com.calamares.estoque.repository;

import com.calamares.estoque.domain.ProdutoFornecedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProdutoFornecedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoFornecedorRepository extends JpaRepository<ProdutoFornecedor, Long> {}
