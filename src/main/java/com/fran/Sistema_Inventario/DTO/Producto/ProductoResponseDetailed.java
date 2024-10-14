package com.fran.Sistema_Inventario.DTO.ProductoDTOs;

import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoDetalladoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long cantidadStock;
    private String categoria;
    private String imageUrl;
    private ProveedorBasicoDTO proveedor;
    private Integer umbralBajoStock;
    private List<MovimientoStock> movimientosStock;
}
