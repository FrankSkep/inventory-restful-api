package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDTO;
import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Producto;
import com.fran.Sistema_Inventario.MapperDTO.ProductoMapperDTO;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.Impl.ReporteService;
import com.fran.Sistema_Inventario.Utils.FileValidator;
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
@RequestMapping("/inventario")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapperDTO productoMapper;
    private final ReporteService reporteService;

    public ProductoController(ProductoService productoService, ProductoMapperDTO productoMapper, ReporteService reporteService) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
        this.reporteService = reporteService;
    }

    // Obtener todos los productos del inventario
    @GetMapping("/")
    public Page<ProductoBasicoDTO> getProducts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productoService.getProductsPage(pageable);
    }

    // Obtener detalles de un producto
    @GetMapping("/detalles/{id}")
    public ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.productDetails(id));
        } catch (
                EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Agregar nuevo producto al inventario
    @PostMapping("/nuevo")
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductoDTO productoRequest,
            @RequestPart(value = "file", required = false) MultipartFile file,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Producto producto = productoService.saveProduct(
                productoMapper.toEntity(productoRequest),
                FileValidator.isValidFile(file) ? file : null);

        return ResponseEntity.ok(productoMapper.toDTO(producto));
    }

    // Editar datos de un producto
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable Long id, @ModelAttribute ProductoDTO productoRequest,
                                            @RequestPart(value = "file", required = false) MultipartFile nuevaImagenOpcional,
                                            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Actualiza datos del producto
        productoRequest.setId(id);
        productoService.updateProduct(productoMapper.toEntityWithId(productoRequest));

        // Si se recibe una imagen, la actualiza
        if (FileValidator.isValidFile(nuevaImagenOpcional)) {
            productoService.updateProductImage(nuevaImagenOpcional, productoRequest.getId());
        }

        return ResponseEntity.ok().body("Producto editado exitosamente");
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoService.deleteProduct(id);
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
            byte[] pdfBytes = reporteService.generarReporteInventario(productoService.getAllProducts());

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
