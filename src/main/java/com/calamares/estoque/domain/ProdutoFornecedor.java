package com.calamares.estoque.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProdutoFornecedor.
 */
@Entity
@Table(name = "produto_fornecedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdutoFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "observacoes")
    private String observacoes;

    @JsonIgnoreProperties(value = { "produtoFornecedor" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Produto produto;

    @JsonIgnoreProperties(value = { "produtoFornecedor" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Fornecedor fornecedor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "produtoFornecedors" }, allowSetters = true)
    private Pedido produtoFornecedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProdutoFornecedor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public ProdutoFornecedor valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public ProdutoFornecedor observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ProdutoFornecedor produto(Produto produto) {
        this.setProduto(produto);
        return this;
    }

    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ProdutoFornecedor fornecedor(Fornecedor fornecedor) {
        this.setFornecedor(fornecedor);
        return this;
    }

    public Pedido getProdutoFornecedor() {
        return this.produtoFornecedor;
    }

    public void setProdutoFornecedor(Pedido pedido) {
        this.produtoFornecedor = pedido;
    }

    public ProdutoFornecedor produtoFornecedor(Pedido pedido) {
        this.setProdutoFornecedor(pedido);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoFornecedor)) {
            return false;
        }
        return id != null && id.equals(((ProdutoFornecedor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoFornecedor{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
