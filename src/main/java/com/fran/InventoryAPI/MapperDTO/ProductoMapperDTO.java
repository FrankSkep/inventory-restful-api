package com.fran.InventoryAPI.MapperDTO;

import com.fran.InventoryAPI.DTO.Producto.ProductoResponseBasic;
import com.fran.InventoryAPI.DTO.Producto.ProductoRequest;
import com.fran.InventoryAPI.DTO.Producto.ProductoResponseDetailed;
import com.fran.InventoryAPI.Entity.Producto;
import com.fran.InventoryAPI.Service.CategoriaService;
import com.fran.InventoryAPI.Service.ProveedorService;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapperDTO {

    private final ProveedorMapperDTO proveedorMapper;
    private final CategoriaService categoriaService;
    private final ProveedorService proveedorService;

    public ProductoMapperDTO(ProveedorMapperDTO proveedorMapper, CategoriaService categoriaService, ProveedorService proveedorService) {
        this.proveedorMapper = proveedorMapper;
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    public ProductoRequest toDTO(Producto producto) {
        return new ProductoRequest(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImagen() != null ? producto.getImagen().getUrl() : null,
                producto.getProveedor().getId(),
                producto.getUmbralBajoStock());
    }

    public ProductoResponseBasic toDTObasic(Producto producto) {
        return new ProductoResponseBasic(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImagen() != null ? producto.getImagen().getUrl() : null
        );
    }

    public ProductoResponseDetailed toDTOdetailed(Producto producto) {

        return new ProductoResponseDetailed(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImagen() != null ? producto.getImagen().getUrl() : null,
                proveedorMapper.toDTObasic(producto.getProveedor()),
                producto.getUmbralBajoStock(), producto.getMovimientosStock()
        );
    }

    // Mapeo para agregar nuevo producto
    public Producto toEntity(ProductoRequest productoDTO) {
        return new Producto(
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidadStock(),
                categoriaService.getByName(productoDTO.getCategoria()),
                proveedorService.getById(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock()
        );
    }

    // Mapeo para editar un producto
    public Producto toEntityWithId(ProductoRequest productoDTO) {
        return new Producto(
                productoDTO.getId(),
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidadStock(),
                categoriaService.getByName(productoDTO.getCategoria()),
                proveedorService.getById(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock()
        );
    }
}
