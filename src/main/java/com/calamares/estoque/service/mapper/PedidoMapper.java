package com.calamares.estoque.service.mapper;

import com.calamares.estoque.domain.Pedido;
import com.calamares.estoque.service.dto.PedidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {}
