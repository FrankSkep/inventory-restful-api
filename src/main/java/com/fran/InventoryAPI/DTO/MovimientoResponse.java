package com.fran.InventoryAPI.DTO;

import com.fran.InventoryAPI.DTO.Producto.ProductoResponseBasic;
import com.fran.InventoryAPI.Entity.MovimientoStock;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovimientoResponse {

    private Long id;
    private MovimientoStock.TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String razon;
    private Long cantidad;
    private ProductoResponseBasic producto;
    private Double costoAdquisicion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
