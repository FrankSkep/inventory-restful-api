package com.fran.inventory_api.controller;

import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.dto.ProductRequest;
import com.fran.inventory_api.entity.Product;
import com.fran.inventory_api.exception.InvalidFileException;
import com.fran.inventory_api.mapper.ProductMapperDTO;
import com.fran.inventory_api.service.ProductService;
import com.fran.inventory_api.service.Impl.ReportService;
import com.fran.inventory_api.service.FileValidator;
import jakarta.validation.Valid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

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

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapperDTO productoMapper;
    private final ReportService reportService;

    public ProductController(ProductService productService, ProductMapperDTO productoMapper, ReportService reportService) {
        this.productService = productService;
        this.productoMapper = productoMapper;
        this.reportService = reportService;
    }

    @GetMapping
    public Page<ProductResponseBasic> getProducts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductsPage(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductDetails(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductRequest> createProduct(
            @Valid @ModelAttribute ProductRequest productRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        if (!FileValidator.isValidFile(file)) {
            throw new InvalidFileException("El archivo subido no es válido.");
        }

        Product product = productService.createProduct(
                productoMapper.toEntity(productRequest),
                file);
        productRequest.setId(product.getId());

        return new ResponseEntity<>(productoMapper.toDTO(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequest productRequest,
                                           @RequestPart(value = "file", required = false) MultipartFile newOptionalImage) {

        productRequest.setId(id);
        productService.updateProduct(productoMapper.toEntityWithId(productRequest));

        if (FileValidator.isValidFile(newOptionalImage)) {
            productService.updateProductImage(newOptionalImage, productRequest.getId());
        }

        return ResponseEntity.ok().body("Producto editado exitosamente");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Producto eliminado exitosamente");
        } catch (
                NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto con ID " + id + " no encontrado.");
        } catch (
                IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error al eliminar la imagen del producto.");
        }
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generarReporteInventario() {
        try {
            byte[] pdfBytes = reportService.generateProductsReport(productService.getAllProducts());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            String filename = "reporte_inventario_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".pdf";
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
