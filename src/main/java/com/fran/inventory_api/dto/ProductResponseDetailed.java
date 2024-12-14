package com.fran.inventory_api.dto;

import com.fran.inventory_api.entity.Movement;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDetailed {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long cantidadStock;
    private String categoria;
    private String imageUrl;
    private SupplierResponseBasic proveedor;
    private Integer umbralBajoStock;
    private List<Movement> movimientosStock;
}
