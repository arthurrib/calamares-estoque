package com.calamares.estoque.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.calamares.estoque.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdutoFornecedorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoFornecedor.class);
        ProdutoFornecedor produtoFornecedor1 = new ProdutoFornecedor();
        produtoFornecedor1.setId(1L);
        ProdutoFornecedor produtoFornecedor2 = new ProdutoFornecedor();
        produtoFornecedor2.setId(produtoFornecedor1.getId());
        assertThat(produtoFornecedor1).isEqualTo(produtoFornecedor2);
        produtoFornecedor2.setId(2L);
        assertThat(produtoFornecedor1).isNotEqualTo(produtoFornecedor2);
        produtoFornecedor1.setId(null);
        assertThat(produtoFornecedor1).isNotEqualTo(produtoFornecedor2);
    }
}
