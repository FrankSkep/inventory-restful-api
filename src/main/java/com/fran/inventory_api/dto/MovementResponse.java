package com.fran.inventory_api.dto;

import com.fran.inventory_api.entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MovementResponse {

    private Long id;
    private Movement.MovementType movementType;
    private LocalDateTime fechaMovimiento;
    private String razon;
    private Long cantidad;
    private ProductResponseBasic producto;
    private Double costoAdquisicion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
