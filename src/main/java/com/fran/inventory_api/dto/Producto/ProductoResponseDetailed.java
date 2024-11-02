package com.fran.inventory_api.dto.Producto;

import com.fran.inventory_api.dto.Proveedor.ProveedorResponseBasic;
import com.fran.inventory_api.entity.Movimiento;

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
    private List<Movimiento> movimientosStock;
}
