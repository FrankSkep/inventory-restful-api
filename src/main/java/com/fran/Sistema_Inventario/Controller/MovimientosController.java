package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Service.Impl.MovimientoServiceImpl;
import com.fran.Sistema_Inventario.Service.Impl.ProductoServiceImpl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    private final MovimientoServiceImpl movimientoService;
    private final ProductoServiceImpl productoService;

    public MovimientosController(MovimientoServiceImpl movimientoService, ProductoServiceImpl productoService) {
        this.movimientoService = movimientoService;
        this.productoService = productoService;
    }

    // Obtener todos los movimientos
    @GetMapping("/")
    public List<MovimientoStock> todosLosMovimientos() {
        return movimientoService.obtenerTodosLosMovimientos();
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody MovimientoStock movimiento) {
        MovimientoStock movimientoRegistrado = productoService.actualizarStock(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
    }
}
