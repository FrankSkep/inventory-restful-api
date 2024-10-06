package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.DTO.MovimientoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fran.Sistema_Inventario.Service.MovimientoService;
import com.fran.Sistema_Inventario.Service.ProductoService;
import com.fran.Sistema_Inventario.Service.Impl.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    private final MovimientoService movimientoService;
    private final ProductoService productoService;
    private final ReporteService reporteService;

    public MovimientosController(MovimientoService movimientoService, ProductoService productoService, ReporteService reporteService) {
        this.movimientoService = movimientoService;
        this.productoService = productoService;
        this.reporteService = reporteService;
    }

    // Obtener todos los movimientos
    @GetMapping("/")
    public List<MovimientoDTO> todosLosMovimientos() {
        return movimientoService.obtenerTodosLosMovimientos();
    }

    // Obtener las entradas de stock
    @GetMapping("/entradas")
    public List<MovimientoDTO> mostrarEntradas() {
        return movimientoService.obtenerEntradas();
    }

    // Obtener las salidas de stock
    @GetMapping("/salidas")
    public List<MovimientoDTO> mostrarSalidas() {
        return movimientoService.obtenerSalidas();
    }

    // Registrar un movimiento de stock
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody MovimientoStock movimiento) {
        try {
            MovimientoStock movimientoRegistrado = productoService.updateStock(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
        } catch (
                IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Generar reporte de movimientos
    @GetMapping("/reporte/{tipo}")
    public ResponseEntity<byte[]> generarReporteInventario(@PathVariable String tipo) {
        // tipo = "general" o "entrada" o "salida"
        try {
            byte[] pdfBytes;
            switch (tipo.toLowerCase()) {
                case "general":
                    pdfBytes = reporteService.generarReporteMovimientos(movimientoService.obtenerTodosLosMovimientos(), tipo.toLowerCase());
                    break;
                case "entrada":
                    pdfBytes = reporteService.generarReporteMovimientos(movimientoService.obtenerEntradas(), tipo.toLowerCase());
                    break;
                case "salida":
                    pdfBytes = reporteService.generarReporteMovimientos(movimientoService.obtenerSalidas(), tipo.toLowerCase());
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
