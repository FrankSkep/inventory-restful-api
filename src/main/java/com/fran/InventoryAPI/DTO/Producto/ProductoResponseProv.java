package com.fran.InventoryAPI.DTO.Producto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseProv {

    Long id;
    String nombre;
    String descripcion;
    Double precio;
    String categoriaNombre;
    String imagenUrl;

}
