package com.calamares.estoque.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.calamares.estoque.domain.Produto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer estoque;

    private Integer estoqueMinimo;

    private BigDecimal valorIdeal;

    @NotNull
    private String tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public BigDecimal getValorIdeal() {
        return valorIdeal;
    }

    public void setValorIdeal(BigDecimal valorIdeal) {
        this.valorIdeal = valorIdeal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoDTO)) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produtoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", estoque=" + getEstoque() +
            ", estoqueMinimo=" + getEstoqueMinimo() +
            ", valorIdeal=" + getValorIdeal() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
