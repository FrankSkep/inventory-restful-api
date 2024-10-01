package com.fran.Sistema_Inventario.MapperDTO;

import com.fran.Sistema_Inventario.DTO.MovimientoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapperDTO {

    private final ProductoMapperDTO productoMapper;

    public MovimientoMapperDTO(ProductoMapperDTO productoMapper) {
        this.productoMapper = productoMapper;
    }

    public MovimientoDTO toMovimientoDTO(MovimientoStock movimiento) {
        return new MovimientoDTO(
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
