package com.fran.Sistema_Inventario.DTO.App.Proveedor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProveedorResponseBasic {

    private Long id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String identificacionFiscal;
}
