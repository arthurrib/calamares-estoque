package com.calamares.estoque.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.calamares.estoque.domain.Fornecedor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FornecedorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String telefone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorDTO)) {
            return false;
        }

        FornecedorDTO fornecedorDTO = (FornecedorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
