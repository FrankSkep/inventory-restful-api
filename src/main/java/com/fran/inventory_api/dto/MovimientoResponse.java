package com.fran.inventory_api.dto;

import com.fran.inventory_api.dto.Producto.ProductoResponseBasic;
import com.fran.inventory_api.entity.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovimientoResponse {

    private Long id;
    private Movimiento.TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String razon;
    private Long cantidad;
    private ProductoResponseBasic producto;
    private Double costoAdquisicion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
