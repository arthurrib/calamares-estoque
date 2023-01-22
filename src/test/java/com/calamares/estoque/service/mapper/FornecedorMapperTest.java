package com.calamares.estoque.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FornecedorMapperTest {

    private FornecedorMapper fornecedorMapper;

    @BeforeEach
    public void setUp() {
        fornecedorMapper = new FornecedorMapperImpl();
    }
}
