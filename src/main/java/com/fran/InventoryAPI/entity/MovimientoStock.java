package com.fran.InventoryAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "movimientos")
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento;

    private String razon;

    @NotNull(message = "La cantidad es obligatoria.")
    private Long cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Producto producto;

    @Min(value = 0, message = "El costo de adquisición debe ser mayor o igual a 0")
    private Double costoAdquisicion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
