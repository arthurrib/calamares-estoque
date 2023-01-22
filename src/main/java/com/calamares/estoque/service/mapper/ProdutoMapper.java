package com.calamares.estoque.service.mapper;

import com.calamares.estoque.domain.Produto;
import com.calamares.estoque.service.dto.ProdutoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produto} and its DTO {@link ProdutoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto> {}
