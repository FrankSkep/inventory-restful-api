package com.fran.Sistema_Inventario.MapperDTO;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.Impl.CategoriaServiceImpl;
import com.fran.Sistema_Inventario.Service.Impl.ProveedorServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapperDTO {

    private final ProveedorMapperDTO proveedorMapper;
    private final CategoriaServiceImpl categoriaService;
    private final ProveedorServiceImpl proveedorService;

    public ProductoMapperDTO(ProveedorMapperDTO proveedorMapper, CategoriaServiceImpl categoriaService, ProveedorServiceImpl proveedorService) {
        this.proveedorMapper = proveedorMapper;
        this.categoriaService = categoriaService;
        this.proveedorService = proveedorService;
    }

    public ProductoDTO toDTO(Producto producto) {
        return new ProductoDTO(
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

    public ProductoBasicoDTO toDTObasic(Producto producto) {
        return new ProductoBasicoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImagen() != null ? producto.getImagen().getUrl() : null
        );
    }

    public ProductoDetalladoDTO toDTOdetailed(Producto producto) {

        return new ProductoDetalladoDTO(
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
    public Producto toEntity(ProductoDTO productoDTO) {
        return new Producto(
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidadStock(),
                categoriaService.obtenerPorNombre(productoDTO.getCategoria()),
                proveedorService.obtenerPorID(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock()
        );
    }

    // Mapeo para editar un producto
    public Producto toEntityWithId(ProductoDTO productoDTO) {
        return new Producto(
                productoDTO.getId(),
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidadStock(),
                categoriaService.obtenerPorNombre(productoDTO.getCategoria()),
                proveedorService.obtenerPorID(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock()
        );
    }
}
