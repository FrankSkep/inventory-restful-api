package com.fran.Sistema_Inventario.DTO.App.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseBasic {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long cantidadStock;
    private String categoria;
    private String imageUrl;
}
