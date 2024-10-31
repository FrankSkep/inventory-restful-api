package com.fran.InventoryAPI.DTO.Proveedor;

import com.fran.InventoryAPI.DTO.Producto.ProductoResponseProv;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProveedorResponseDetailed {

    private Long id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String identificacionFiscal;
    private Set<ProductoResponseProv> productos;
}
