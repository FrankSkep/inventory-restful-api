package com.fran.inventory_api.system.service.Impl;

import com.fran.inventory_api.system.dto.ProductResponseBasic;
import com.fran.inventory_api.system.dto.ProductResponseDetailed;
import com.fran.inventory_api.system.entity.Image;
import com.fran.inventory_api.system.entity.Movement;
import com.fran.inventory_api.system.entity.Product;
import com.fran.inventory_api.system.exception.InsufficientStockException;
import com.fran.inventory_api.system.exception.ProductNotFoundException;
import com.fran.inventory_api.system.exception.RequiredValueException;
import com.fran.inventory_api.system.mapper.ProductMapper;
import com.fran.inventory_api.system.repository.ProductRepository;
import com.fran.inventory_api.system.service.ImageService;
import com.fran.inventory_api.system.service.NotificationService;
import com.fran.inventory_api.system.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productoMapper;
    private final ImageService imageService;
    private final NotificationService notificationService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productoMapper, ImageService imageService,
                              NotificationService notificationService) {
        this.productRepository = productRepository;
        this.productoMapper = productoMapper;
        this.imageService = imageService;
        this.notificationService = notificationService;
    }

    private Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    // get all products basic info
    @Override
    public List<ProductResponseBasic> getAllProducts() {
        return productRepository.findAllBasic();
    }

    // get all products basic info with pagination
    @Override
    public Page<ProductResponseBasic> getProductsPage(Pageable pageable) {
        return productRepository.findAllBasic(pageable);
    }

    // get product details
    @Override
    public ProductResponseDetailed getProductDetails(Long id) {

        Product product = getById(id);

        return productoMapper.toDTOdetailed(product);
    }

    // save a new product
    @Transactional
    @Override
    public Product createProduct(Product product, MultipartFile file) {

        if (file != null) {
            Image image = imageService.uploadImage(file);
            product.setImage(image);
        }
        return productRepository.save(product);
    }

    // update a existing product
    @Override
    public void updateProduct(Product product) {
        Product productDB = getById(product.getId());

        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        productDB.setCategory(product.getCategory());
        productDB.setSupplier(product.getSupplier());
        productDB.setMinStock(product.getMinStock());
        productRepository.save(productDB);
    }

    // update the image of a product
    @Override
    @Transactional
    public void updateProductImage(MultipartFile file, Long productId) {

        Product productDB = getById(productId);
        Image imageActual = productDB.getImage();

        if (imageActual != null) {
            imageService.deleteFromCloudinary(imageActual.getImageId());
            imageActual = imageService.updateImage(file, imageActual);
        } else {
            Image imageNueva = imageService.uploadImage(file);
            productDB.setImage(imageNueva);
        }
        productRepository.save(productDB);
    }

    // delete a product
    @Override
    public void deleteProduct(Long id) {
        Product product = getById(id);

        // delete the image if exists
        if (product.getImage() != null) {
            imageService.completeDeletion(product.getImage());
        }

        // delete the product
        productRepository.delete(product);
    }

    // update the stock of a product
    @Override
    @Transactional
    public Movement updateStock(Movement movement) {

        Product product = getById(movement.getProduct().getId());

        Long currentStock = product.getStock();
        Long movementStock = movement.getQuantity();

        if (movement.getType() == Movement.MovementType.EXIT) {
            if (currentStock < movementStock) {
                throw new InsufficientStockException("Insufficient stock for the movement");
            }
            movement.setAcquisitionCost(null);
            product.setStock(currentStock - movementStock);
        } else {
            if (movement.getAcquisitionCost() == null || movement.getAcquisitionCost() <= 0) {
                throw new RequiredValueException("The acquisition cost is required");
            }
            product.setStock(currentStock + movementStock);
        }

        if (product.getStock() <= product.getMinStock()) {
            notificationService.sendNotification("The product " + product.getName() + " is below the minimum stock");
        }

        movement.setProduct(product);
        productRepository.save(product);
        return movement;
    }
}
