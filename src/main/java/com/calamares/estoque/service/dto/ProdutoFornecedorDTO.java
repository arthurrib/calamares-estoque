package com.calamares.estoque.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.calamares.estoque.domain.ProdutoFornecedor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoFornecedorDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal valor;

    private String observacoes;

    private ProdutoDTO produto;

    private FornecedorDTO fornecedor;

    private PedidoDTO produtoFornecedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public FornecedorDTO getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorDTO fornecedor) {
        this.fornecedor = fornecedor;
    }

    public PedidoDTO getProdutoFornecedor() {
        return produtoFornecedor;
    }

    public void setProdutoFornecedor(PedidoDTO produtoFornecedor) {
        this.produtoFornecedor = produtoFornecedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoFornecedorDTO)) {
            return false;
        }

        ProdutoFornecedorDTO produtoFornecedorDTO = (ProdutoFornecedorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produtoFornecedorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoFornecedorDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", observacoes='" + getObservacoes() + "'" +
            ", produto=" + getProduto() +
            ", fornecedor=" + getFornecedor() +
            ", produtoFornecedor=" + getProdutoFornecedor() +
            "}";
    }
}
