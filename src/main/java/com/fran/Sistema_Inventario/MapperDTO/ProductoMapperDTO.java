package com.fran.Sistema_Inventario.MapperDTO;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.Impl.CategoriaServiceImpl;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapperDTO {

    private ProveedorService proveedorService;
    private ProveedorMapperDTO proveedorMapper;
    private CategoriaServiceImpl categoriaService;

    public ProductoMapperDTO(ProveedorService proveedorService, ProveedorMapperDTO proveedorMapper, CategoriaServiceImpl categoriaService) {
        this.proveedorService = proveedorService;
        this.proveedorMapper = proveedorMapper;
        this.categoriaService = categoriaService;
    }
    
    public ProductoDTO toDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria().getNombre(),
                producto.getImageUrl(),
                producto.getImageId(),
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
                producto.getImageUrl()
        );
    }

    public ProductoDetalladoDTO toDTOdetailed(Producto producto) {

        return new ProductoDetalladoDTO(
                producto.getId(), producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getCantidadStock(),
                producto.getCategoria().getNombre(), producto.getImageUrl(),
                proveedorMapper.toDTObasic(producto.getProveedor()),
                producto.getUmbralBajoStock(), producto.getMovimientosStock()
        );
    }

    public Producto toEntity(ProductoDTO productoDTO) {
        return new Producto(
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getCantidadStock(),
                categoriaService.getCategoriaByNombre(productoDTO.getCategoria()),
                productoDTO.getImageUrl(),
                productoDTO.getImageId(),
                proveedorService.obtenerPorID(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock());
    }
}
