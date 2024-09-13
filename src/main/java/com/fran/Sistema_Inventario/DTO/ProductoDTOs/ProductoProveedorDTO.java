package com.fran.Sistema_Inventario.DTO.ProductoDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoProveedorDTO {

    Long id;
    String nombre;
    String descripcion;
    Double precio;
    String categoriaNombre;
    String imagenUrl;

}

