package com.fran.Sistema_Inventario.DTO.ProveedorDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProveedorBasicoDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String identificacionFiscal;
}
