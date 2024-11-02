package com.fran.InventoryAPI.dto_mapper;

import com.fran.InventoryAPI.dto.MovimientoResponse;
import com.fran.InventoryAPI.entity.MovimientoStock;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapperDTO {

    private final ProductoMapperDTO productoMapper;

    public MovimientoMapperDTO(ProductoMapperDTO productoMapper) {
        this.productoMapper = productoMapper;
    }

    public MovimientoResponse toDTO(MovimientoStock movimiento) {
        return new MovimientoResponse(
                movimiento.getId(),
                movimiento.getTipoMovimiento(),
                movimiento.getFechaMovimiento(),
                movimiento.getRazon(),
                movimiento.getCantidad(),
                productoMapper.toDTObasic(movimiento.getProducto()),
                movimiento.getCostoAdquisicion()
        );
    }

}
