package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.dto.ProductResponseDetailed;
import com.fran.inventory_api.entity.Image;
import com.fran.inventory_api.entity.Movement;
import com.fran.inventory_api.entity.Product;
import com.fran.inventory_api.exception.InsufficientStockException;
import com.fran.inventory_api.exception.RequiredValueException;
import com.fran.inventory_api.mapper.ProductMapperDTO;
import com.fran.inventory_api.exception.ProductNotFoundException;
import com.fran.inventory_api.repository.MovementRepository;
import com.fran.inventory_api.repository.ProductRepository;
import com.fran.inventory_api.service.ImageService;
import com.fran.inventory_api.service.NotificationService;
import com.fran.inventory_api.service.ProductService;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MovementRepository movimientoStockRepository;
    private final ProductMapperDTO productoMapper;
    private final ImageService imageService;
    private final NotificationService notificationService;

    public ProductServiceImpl(ProductRepository productRepository, MovementRepository movimientoStockRepository,
                              ProductMapperDTO productoMapper, ImageService imageService,
                              NotificationService notificationService) {
        this.productRepository = productRepository;
        this.movimientoStockRepository = movimientoStockRepository;
        this.productoMapper = productoMapper;
        this.imageService = imageService;
        this.notificationService = notificationService;
    }

    // Obtener todos los productos y sus datos basicos
    @Override
    public List<ProductResponseBasic> getAllProducts() {
        return productRepository.findAllBasic();
    }

    @Override
    public Page<ProductResponseBasic> getProductsPage(Pageable pageable) {
        return productRepository.findAllBasic(pageable);
    }

    // Obtener detalles de un producto
    @Override
    public ProductResponseDetailed getProductDetails(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        return productoMapper.toDTOdetailed(product);
    }

    // Guardar un nuevo producto
    @Transactional
    @Override
    public Product save(Product product, MultipartFile file) {

        if (file != null) {
            Image image = imageService.uploadImage(file);
            product.setImage(image);
        }
        return productRepository.save(product);
    }

    // Editar datos de un producto existente
    @Override
    public void update(Product product) {

        Product productDB = productRepository.getReferenceById(product.getId());

        productDB.setNombre(product.getNombre());
        productDB.setDescripcion(product.getDescripcion());
        productDB.setPrecio(product.getPrecio());
        productDB.setCategory(product.getCategory());
        productDB.setSupplier(product.getSupplier());
        productDB.setUmbralBajoStock(product.getUmbralBajoStock());
        productRepository.save(productDB);
    }

    // Actualizar la imagen de un producto o la agrega si no existe
    @Override
    @Transactional
    public void updateImage(MultipartFile file, Long productoId) {

        Product productDB = productRepository.getReferenceById(productoId);
        Image imageActual = productDB.getImage();

        if (imageActual != null) {
            imageService.deleteFromCloudinary(imageActual.getImageId());
            imageActual = imageService.update(file, imageActual);
        } else {
            Image imageNueva = imageService.uploadImage(file);
            productDB.setImage(imageNueva);
        }
        productRepository.save(productDB);
    }

    // Eliminar un producto
    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        // Eliminar la imagen si existe
        if (product.getImage() != null) {
            imageService.completeDeletion(product.getImage());
        }

        // Eliminar el producto
        productRepository.delete(product);
    }

    // Actualiza el stock y registra el movimiento
    @Override
    @Transactional
    public Movement updateStock(Movement movement) {

        Product product = productRepository.getReferenceById(movement.getProduct().getId());

        Long stockActual = product.getCantidadStock();
        Long stockMovimiento = movement.getCantidad();

        if (movement.getTipoMovimiento() == Movement.TipoMovimiento.SALIDA) {
            if (stockActual < stockMovimiento) {
                throw new InsufficientStockException("No hay suficiente stock para realizar esta operación");
            }
            movement.setCostoAdquisicion(null);
            product.setCantidadStock(stockActual - stockMovimiento);
        } else {
            if (movement.getCostoAdquisicion() == null || movement.getCostoAdquisicion() <= 0) {
                throw new RequiredValueException("El costo de adquisición es obligatorio para entradas de stock.");
            }
            product.setCantidadStock(stockActual + stockMovimiento);
        }

        if (product.getCantidadStock() <= product.getUmbralBajoStock()) {
            sendNotification("El producto " + product.getNombre() + " está bajo de stock");
        }

        movement.setProduct(product);
        movement.setFechaMovimiento(LocalDateTime.now());

        productRepository.save(product);
        return movimientoStockRepository.save(movement);
    }

    @Override
    public void sendNotification(String message) {
        notificationService.sendNotification(message);
    }
}
