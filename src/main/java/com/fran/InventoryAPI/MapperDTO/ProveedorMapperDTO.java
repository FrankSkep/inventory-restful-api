package com.fran.InventoryAPI.MapperDTO;

import com.fran.InventoryAPI.DTO.Producto.ProductoResponseProv;
import com.fran.InventoryAPI.DTO.Proveedor.ProveedorResponseBasic;
import com.fran.InventoryAPI.DTO.Proveedor.ProveedorRequest;
import com.fran.InventoryAPI.DTO.Proveedor.ProveedorResponseDetailed;
import com.fran.InventoryAPI.Entity.Producto;
import com.fran.InventoryAPI.Entity.Proveedor;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ProveedorMapperDTO {

    public ProveedorResponseBasic toDTObasic(Proveedor proveedor) {
        return new ProveedorResponseBasic(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getIdentificacionFiscal()
        );
    }

    public ProveedorRequest toDTO(Proveedor proveedor) {
        return new ProveedorRequest(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getIdentificacionFiscal()
        );
    }

    public ProveedorResponseDetailed toDTOdetailed(Proveedor proveedor) {

        Set<Producto> productos = proveedor.getProductos();
        Set<ProductoResponseProv> setDeProductos = productos.stream().map(this::productoToDTOminimo).collect(Collectors.toSet());

        return new ProveedorResponseDetailed(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getDireccion(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getIdentificacionFiscal(),
                setDeProductos
        );
    }

    public ProductoResponseProv productoToDTOminimo(Producto producto) {
        return new ProductoResponseProv(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoria().getNombre(),
                producto.getImagen() != null ? producto.getImagen().getUrl() : null
        );
    }

    // Mapeo para agregacion de nuevo proveedor
    public Proveedor toEntity(ProveedorRequest dto) {
        return new Proveedor(
                dto.getNombre(),
                dto.getDireccion(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdentificacionFiscal()
        );
    }

    // Mapeo para edicion de proveedor
    public Proveedor toEntityFromDTOWithId(ProveedorRequest dto) {
        return new Proveedor(
                dto.getId(),
                dto.getNombre(),
                dto.getDireccion(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getIdentificacionFiscal()
        );
    }


}
