package com.fran.Sistema_Inventario.DTO;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovimientoDTO {

    private Long id;
    private MovimientoStock.TipoMovimiento tipoMovimiento;
    private LocalDateTime fechaMovimiento;
    private String razon;
    private Long cantidad;
    private ProductoBasicoDTO producto;
    private Double costoAdquisicion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
