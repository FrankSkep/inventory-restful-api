package com.fran.Sistema_Inventario.DTO.App.Producto;

import com.fran.Sistema_Inventario.DTO.App.Proveedor.ProveedorResponseBasic;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoResponseDetailed {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long cantidadStock;
    private String categoria;
    private String imageUrl;
    private ProveedorResponseBasic proveedor;
    private Integer umbralBajoStock;
    private List<MovimientoStock> movimientosStock;
}
