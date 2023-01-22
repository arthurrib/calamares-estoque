package com.calamares.estoque.service.mapper;

import com.calamares.estoque.domain.Fornecedor;
import com.calamares.estoque.domain.Pedido;
import com.calamares.estoque.domain.Produto;
import com.calamares.estoque.domain.ProdutoFornecedor;
import com.calamares.estoque.service.dto.FornecedorDTO;
import com.calamares.estoque.service.dto.PedidoDTO;
import com.calamares.estoque.service.dto.ProdutoDTO;
import com.calamares.estoque.service.dto.ProdutoFornecedorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProdutoFornecedor} and its DTO {@link ProdutoFornecedorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProdutoFornecedorMapper extends EntityMapper<ProdutoFornecedorDTO, ProdutoFornecedor> {
    @Mapping(target = "produto", source = "produto", qualifiedByName = "produtoId")
    @Mapping(target = "fornecedor", source = "fornecedor", qualifiedByName = "fornecedorId")
    @Mapping(target = "produtoFornecedor", source = "produtoFornecedor", qualifiedByName = "pedidoId")
    ProdutoFornecedorDTO toDto(ProdutoFornecedor s);

    @Named("produtoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProdutoDTO toDtoProdutoId(Produto produto);

    @Named("fornecedorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FornecedorDTO toDtoFornecedorId(Fornecedor fornecedor);

    @Named("pedidoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PedidoDTO toDtoPedidoId(Pedido pedido);
}
