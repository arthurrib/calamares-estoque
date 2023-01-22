package com.calamares.estoque.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_pedido", nullable = false)
    private LocalDate dataPedido;

    @Column(name = "status")
    private String status;

    @Column(name = "valor_total", precision = 21, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "produtoFornecedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produto", "fornecedor", "produtoFornecedor" }, allowSetters = true)
    private Set<ProdutoFornecedor> produtoFornecedors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pedido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return this.dataPedido;
    }

    public Pedido dataPedido(LocalDate dataPedido) {
        this.setDataPedido(dataPedido);
        return this;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return this.status;
    }

    public Pedido status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public Pedido valorTotal(BigDecimal valorTotal) {
        this.setValorTotal(valorTotal);
        return this;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Set<ProdutoFornecedor> getProdutoFornecedors() {
        return this.produtoFornecedors;
    }

    public void setProdutoFornecedors(Set<ProdutoFornecedor> produtoFornecedors) {
        if (this.produtoFornecedors != null) {
            this.produtoFornecedors.forEach(i -> i.setProdutoFornecedor(null));
        }
        if (produtoFornecedors != null) {
            produtoFornecedors.forEach(i -> i.setProdutoFornecedor(this));
        }
        this.produtoFornecedors = produtoFornecedors;
    }

    public Pedido produtoFornecedors(Set<ProdutoFornecedor> produtoFornecedors) {
        this.setProdutoFornecedors(produtoFornecedors);
        return this;
    }

    public Pedido addProdutoFornecedor(ProdutoFornecedor produtoFornecedor) {
        this.produtoFornecedors.add(produtoFornecedor);
        produtoFornecedor.setProdutoFornecedor(this);
        return this;
    }

    public Pedido removeProdutoFornecedor(ProdutoFornecedor produtoFornecedor) {
        this.produtoFornecedors.remove(produtoFornecedor);
        produtoFornecedor.setProdutoFornecedor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return id != null && id.equals(((Pedido) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", dataPedido='" + getDataPedido() + "'" +
            ", status='" + getStatus() + "'" +
            ", valorTotal=" + getValorTotal() +
            "}";
    }
}
