package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final ProveedorService proveedorService;
    private final ProductoMapperDTO productoMapper;
    private CloudinaryServiceImpl cloudinaryService;
    private CategoriaServiceImpl categoriaService;

    public ProductoServiceImpl(ProductoRepository productoRepository, MovimientoStockRepository movimientoStockRepository,
            ProveedorService proveedorService, ProductoMapperDTO productoMapper, CloudinaryServiceImpl cloudinaryService,
            CategoriaServiceImpl categoriaService) {
        this.productoRepository = productoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.proveedorService = proveedorService;
        this.productoMapper = productoMapper;
        this.cloudinaryService = cloudinaryService;
        this.categoriaService = categoriaService;
    }

    @Override
    public List<ProductoBasicoDTO> obtenerProductos() {

        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(productoMapper::toDTObasic).collect(Collectors.toList());
    }

    // Obtener producto por su ID
    @Override
    public ProductoDetalladoDTO detallesProducto(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        return productoMapper.toDTOdetailed(producto);
    }

    // Guardar un nuevo producto
    @Override
    public Producto guardarProducto(ProductoDTO productoReq) {

        Producto producto = productoMapper.toEntity(productoReq);

        return productoRepository.save(producto);
    }

    // Editar datos de un producto existente
    @Override
    public void editarProducto(Long id, ProductoDTO productoReq) {

        Producto productoDB = productoRepository.getReferenceById(id);

        productoDB.setNombre(productoReq.getNombre());
        productoDB.setDescripcion(productoReq.getDescripcion());
        productoDB.setPrecio(productoReq.getPrecio());
        productoDB.setCategoria(categoriaService.getCategoriaByNombre(productoReq.getCategoria()));
        productoDB.setProveedor(proveedorService.obtenerPorID(productoReq.getProveedorId()));
        productoDB.setUmbralBajoStock(productoReq.getUmbralBajoStock());

        productoRepository.save(productoDB);
    }

    @Override
    public void updateFile(Long productoId, MultipartFile file) {
        try {
            Producto producto = productoRepository.getReferenceById(productoId);
            cloudinaryService.deleteFile(producto.getImageId());
            Map uploadResult = cloudinaryService.uploadFile(file);
            String imageUrl = (String) uploadResult.get("url");
            String newImageId = (String) uploadResult.get("public_id");
            producto.setImageId(newImageId);
            producto.setImageUrl(imageUrl);
            productoRepository.save(producto);
        } catch (Exception e) {
            System.out.println("Error al actualizar la imagen: " + e.getMessage());
        }
    }

    // Eliminar un producto
    @Override
    public boolean eliminarProducto(Long id) {

        Producto producto = productoRepository.getReferenceById(id);

        if (producto != null) {
            String publicId = producto.getImageId();

            if (publicId != null && !publicId.isEmpty()) {
                // Eliminar la imagen de Cloudinary usando el public_id
                cloudinaryService.deleteFile(publicId);
            }

            // Eliminar el producto de la base de datos
            productoRepository.delete(producto);
            return true;
        } else {
            return false;
        }
    }

    // Actualiza el stock y registra el movimiento
    @Override
    @Transactional
    public MovimientoStock actualizarStock(MovimientoStock movimiento) {

        Producto producto = productoRepository.getReferenceById(movimiento.getProducto().getId());

        Long stockActual = producto.getCantidadStock();
        Long stockMovimiento = movimiento.getCantidad();

        if (movimiento.getTipoMovimiento() == MovimientoStock.TipoMovimiento.SALIDA) {
            if (stockActual < stockMovimiento) {
                throw new IllegalArgumentException("No hay suficiente stock para realizar esta operación");
            }
            producto.setCantidadStock(stockActual - stockMovimiento);
        } else {
            producto.setCantidadStock(stockActual + stockMovimiento);
        }

        if (producto.getCantidadStock() <= producto.getUmbralBajoStock()) {
            enviarAlertaStock();
        }

        movimiento.setProducto(producto);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        productoRepository.save(producto);
        return movimientoStockRepository.save(movimiento);
    }

    public void enviarAlertaStock() {
        // Aqui implementare la notificacion por correo

    }
}
