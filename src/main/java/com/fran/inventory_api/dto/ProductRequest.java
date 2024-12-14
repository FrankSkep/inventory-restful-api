package com.fran.inventory_api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductRequest {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock debe ser mayor o igual a 0")
    private Long cantidadStock;

    @NotBlank(message = "Debe seleccionar una categoría")
    private String categoria;

    private String imageUrl;

    @NotNull(message = "Debe seleccionar un proveedor")
    private Long proveedorId;

    @NotNull(message = "Debe especificar el umbral de alerta de stock")
    @Min(value = 0, message = "El umbral de alerta debe ser mayor o igual a 0")
    private Integer umbralBajoStock;
}
