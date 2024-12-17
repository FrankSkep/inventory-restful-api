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
    private final ProductMapperDTO productoMapper;
    private final ImageService imageService;
    private final NotificationService notificationService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapperDTO productoMapper, ImageService imageService,
                              NotificationService notificationService) {
        this.productRepository = productRepository;
        this.productoMapper = productoMapper;
        this.imageService = imageService;
        this.notificationService = notificationService;
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

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productoMapper.toDTOdetailed(product);
    }

    // save a new product
    @Transactional
    @Override
    public Product save(Product product, MultipartFile file) {

        if (file != null) {
            Image image = imageService.uploadImage(file);
            product.setImage(image);
        }
        return productRepository.save(product);
    }

    // update a existing product
    @Override
    public void update(Product product) {
        Product productDB = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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

    // delete a product
    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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

        Product product = productRepository.getReferenceById(movement.getProduct().getId());

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
        movement.setDate(LocalDateTime.now());

        productRepository.save(product);
        return movement;
    }
}
