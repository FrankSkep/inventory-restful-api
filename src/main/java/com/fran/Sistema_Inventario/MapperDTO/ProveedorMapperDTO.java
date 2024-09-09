package com.fran.Sistema_Inventario.MapperDTO;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapperDTO {

    public ProveedorBasicoDTO toDTObasic(Proveedor proveedor) {
        return new ProveedorBasicoDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getIdentificacionFiscal()
        );
    }

    public ProveedorDTO toDTO(Proveedor proveedor) {
        return new ProveedorDTO(
                proveedor.getId(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getEmail(), proveedor.getTelefono(),
                proveedor.getIdentificacionFiscal()
        );
    }

    public ProveedorDetalladoDTO toDTOdetailed(Proveedor proveedor) {

        Set<Producto> productos = proveedor.getProductos();
        Set<ProductoBasicoDTO> productosSet = productos.stream().map(this::productoToDTObasic).collect(Collectors.toSet());

        return new ProveedorDetalladoDTO(
                proveedor.getId(), proveedor.getNombre(), proveedor.getDireccion(),
                proveedor.getEmail(), proveedor.getTelefono(), proveedor.getIdentificacionFiscal(),
                productosSet
        );
    }

    public ProductoBasicoDTO productoToDTObasic(Producto producto) {
        return new ProductoBasicoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImageUrl()
        );
    }

    public Proveedor toEntityFromDTO(ProveedorDTO dto) {
        return new Proveedor(
                dto.getNombre(),
                dto.getDireccion(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdentificacionFiscal()
        );
    }
}
