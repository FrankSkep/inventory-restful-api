package com.fran.inventory_api.system.controller;

import com.fran.inventory_api.system.dto.ProductRequest;
import com.fran.inventory_api.system.dto.ProductResponseBasic;
import com.fran.inventory_api.system.dto.ProductResponseDetailed;
import com.fran.inventory_api.system.entity.Product;
import com.fran.inventory_api.system.exception.InvalidFileException;
import com.fran.inventory_api.system.mapper.ProductMapper;
import com.fran.inventory_api.system.service.FileValidator;
import com.fran.inventory_api.system.service.Impl.ReportService;
import com.fran.inventory_api.system.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ReportService reportService;

    public ProductController(ProductService productService, ProductMapper productMapper, ReportService reportService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseBasic>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getProductsPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDetailed> getProductDetails(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetails(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<ProductRequest> createProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        if (file != null && !FileValidator.isValidFile(file)) {
            throw new InvalidFileException("The file uploaded is not a valid image.");
        }

        Product product = productService.createProduct(
                productMapper.toEntity(productRequest),
                file);
        productRequest.setId(product.getId());

        return ResponseEntity.created(URI.create("/api/products/" + product.getId())).body(productMapper.toDTO(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequest productRequest,
                                              @RequestPart(value = "file", required = false) MultipartFile newOptionalImage) {

        productRequest.setId(id);
        productService.updateProduct(productMapper.toEntityWithId(productRequest));

        if (FileValidator.isValidFile(newOptionalImage)) {
            productService.updateProductImage(newOptionalImage, productRequest.getId());
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generarReporteInventario() {
        try {
            byte[] pdfBytes = reportService.generateProductsReport(productService.getAllProducts());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            String filename = "inventory_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".pdf";
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (
                Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
