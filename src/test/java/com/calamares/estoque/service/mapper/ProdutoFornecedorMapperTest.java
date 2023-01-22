package com.calamares.estoque.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProdutoFornecedorMapperTest {

    private ProdutoFornecedorMapper produtoFornecedorMapper;

    @BeforeEach
    public void setUp() {
        produtoFornecedorMapper = new ProdutoFornecedorMapperImpl();
    }
}
