package com.fran.Sistema_Inventario.MapperDTO;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.Service.Impl.ProveedorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    @Autowired
    private ProveedorServiceImpl proveedorServiceImpl;

    @Autowired
    private ProveedorMapperDTO proveedorMapper;

    public ProductoDTO toDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCantidadStock(),
                producto.getCategoria(),
                producto.getImageUrl(),
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
                producto.getCategoria(),
                producto.getImageUrl()
        );
    }

    public ProductoDetalladoDTO toDTOdetailed(Producto producto) {

        return new ProductoDetalladoDTO(
                producto.getId(), producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getCantidadStock(),
                producto.getCategoria(), producto.getImageUrl(),
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
                productoDTO.getCategoria(),
                productoDTO.getImageUrl(),
                proveedorServiceImpl.obtenerPorID(productoDTO.getProveedorId()),
                productoDTO.getUmbralBajoStock());
    }
}
