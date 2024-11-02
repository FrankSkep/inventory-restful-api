package com.fran.inventory_api.controller;

import com.fran.inventory_api.dto.Producto.ProductoResponseBasic;
import com.fran.inventory_api.dto.Producto.ProductoRequest;
import com.fran.inventory_api.entity.Producto;
import com.fran.inventory_api.dto_mapper.ProductoMapperDTO;
import com.fran.inventory_api.service.ProductoService;
import com.fran.inventory_api.service.Impl.ReporteService;
import com.fran.inventory_api.service.FileValidator;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapperDTO productoMapper;
    private final ReporteService reporteService;

    public ProductoController(ProductoService productoService, ProductoMapperDTO productoMapper, ReporteService reporteService) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
        this.reporteService = reporteService;
    }

    // Obtener productos paginados
    @GetMapping("/")
    public Page<ProductoResponseBasic> getProducts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoService.getProductsPage(pageable);
    }

    // Obtener detalles de un producto
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.getProductDetails(id));
        } catch (
                EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Agregar nuevo producto al inventario
    @PostMapping
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductoRequest productoRequest,
            @RequestPart(value = "file", required = false) MultipartFile file,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Producto product = productoService.save(
                productoMapper.toEntity(productoRequest),
                FileValidator.isValidFile(file) ? file : null);

        return ResponseEntity.ok(productoMapper.toDTO(product));
    }

    // Editar datos de un producto
    @PutMapping
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductoRequest productoRequest,
                                           @RequestPart(value = "file", required = false) MultipartFile newOptionalImage,
                                           BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Actualiza datos del producto
        productoRequest.setId(id);
        productoService.update(productoMapper.toEntityWithId(productoRequest));

        // Si se recibe una imagen, la actualiza
        if (FileValidator.isValidFile(newOptionalImage)) {
            productoService.updateImage(newOptionalImage, productoRequest.getId());
        }

        return ResponseEntity.ok().body("Producto editado exitosamente");
    }

    // Eliminar un producto por su ID
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productoService.delete(id);
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

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporteInventario() {
        try {
            byte[] pdfBytes = reporteService.genInventoryReport(productoService.getAllProducts());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

            String filename = "reporte_inventario_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".pdf";
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
