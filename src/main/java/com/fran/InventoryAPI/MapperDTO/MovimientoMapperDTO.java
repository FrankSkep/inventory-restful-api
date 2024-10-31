package com.fran.InventoryAPI.MapperDTO;

import com.fran.InventoryAPI.DTO.MovimientoResponse;
import com.fran.InventoryAPI.Entity.MovimientoStock;
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
