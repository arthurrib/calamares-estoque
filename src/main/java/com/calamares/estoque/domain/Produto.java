package com.calamares.estoque.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "estoque", nullable = false)
    private Integer estoque;

    @Column(name = "estoque_minimo")
    private Integer estoqueMinimo;

    @Column(name = "valor_ideal", precision = 21, scale = 2)
    private BigDecimal valorIdeal;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @JsonIgnoreProperties(value = { "produto", "fornecedor", "produtoFornecedor" }, allowSetters = true)
    @OneToOne(mappedBy = "produto")
    private ProdutoFornecedor produtoFornecedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Produto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEstoque() {
        return this.estoque;
    }

    public Produto estoque(Integer estoque) {
        this.setEstoque(estoque);
        return this;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Integer getEstoqueMinimo() {
        return this.estoqueMinimo;
    }

    public Produto estoqueMinimo(Integer estoqueMinimo) {
        this.setEstoqueMinimo(estoqueMinimo);
        return this;
    }

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public BigDecimal getValorIdeal() {
        return this.valorIdeal;
    }

    public Produto valorIdeal(BigDecimal valorIdeal) {
        this.setValorIdeal(valorIdeal);
        return this;
    }

    public void setValorIdeal(BigDecimal valorIdeal) {
        this.valorIdeal = valorIdeal;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Produto tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ProdutoFornecedor getProdutoFornecedor() {
        return this.produtoFornecedor;
    }

    public void setProdutoFornecedor(ProdutoFornecedor produtoFornecedor) {
        if (this.produtoFornecedor != null) {
            this.produtoFornecedor.setProduto(null);
        }
        if (produtoFornecedor != null) {
            produtoFornecedor.setProduto(this);
        }
        this.produtoFornecedor = produtoFornecedor;
    }

    public Produto produtoFornecedor(ProdutoFornecedor produtoFornecedor) {
        this.setProdutoFornecedor(produtoFornecedor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", estoque=" + getEstoque() +
            ", estoqueMinimo=" + getEstoqueMinimo() +
            ", valorIdeal=" + getValorIdeal() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
