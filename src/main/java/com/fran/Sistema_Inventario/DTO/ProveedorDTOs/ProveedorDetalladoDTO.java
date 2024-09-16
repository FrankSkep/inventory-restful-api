package com.fran.Sistema_Inventario.DTO.ProveedorDTOs;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoProveedorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProveedorDetalladoDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String identificacionFiscal;
    private Set<ProductoProveedorDTO> productos;
}
