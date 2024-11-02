package com.fran.InventoryAPI.controller;

import com.fran.InventoryAPI.dto.MovimientoResponse;
import com.fran.InventoryAPI.entity.MovimientoStock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fran.InventoryAPI.service.MovimientoService;
import com.fran.InventoryAPI.service.ProductoService;
import com.fran.InventoryAPI.service.Impl.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientosController {

    private final MovimientoService movimientoService;
    private final ProductoService productoService;
    private final ReporteService reporteService;

    public MovimientosController(MovimientoService movimientoService, ProductoService productoService, ReporteService reporteService) {
        this.movimientoService = movimientoService;
        this.productoService = productoService;
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<MovimientoResponse> todosLosMovimientos() {
        return movimientoService.getAll();
    }

    @GetMapping("/entradas")
    public List<MovimientoResponse> getEntries() {
        return movimientoService.getEntries();
    }

    @GetMapping("/salidas")
    public List<MovimientoResponse> getOutputs() {
        return movimientoService.getOutputs();
    }

    @PostMapping
    public ResponseEntity<?> addMovement(@RequestBody MovimientoStock movimiento) {
        try {
            MovimientoStock movement = productoService.updateStock(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(movement);
        } catch (
                IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllMovements() {
        movimientoService.deleteAllMovements();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/salidas")
    public ResponseEntity<?> deleteAllOutputs() {
        movimientoService.deleteAllOutputs();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/entradas")
    public ResponseEntity<?> deleteAllEntries() {
        movimientoService.deleteAllEntries();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Generar reporte de movimientos
    @GetMapping("/reporte/{tipo}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String tipo) {
        // tipo = "general" o "entrada" o "salida"
        try {
            byte[] pdfBytes;
            switch (tipo.toLowerCase()) {
                case "general":
                    pdfBytes = reporteService.genMovementsReport(movimientoService.getAll(), tipo.toLowerCase());
                    break;
                case "entrada":
                    pdfBytes = reporteService.genMovementsReport(movimientoService.getEntries(), tipo.toLowerCase());
                    break;
                case "salida":
                    pdfBytes = reporteService.genMovementsReport(movimientoService.getOutputs(), tipo.toLowerCase());
                    break;
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

            String filename = "reporte_movimientos_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".pdf";
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
