package com.calamares.estoque.service.mapper;

import com.calamares.estoque.domain.Fornecedor;
import com.calamares.estoque.service.dto.FornecedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fornecedor} and its DTO {@link FornecedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorMapper extends EntityMapper<FornecedorDTO, Fornecedor> {}
