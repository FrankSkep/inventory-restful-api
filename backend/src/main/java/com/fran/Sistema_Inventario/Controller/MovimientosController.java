package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Service.MovimientoService;
import com.fran.Sistema_Inventario.Service.ProductoServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private ProductoServiceImpl productoService;

    @PostMapping("/registrar")
    public ResponseEntity<MovimientoStock> registrarMovimientoStock(@RequestBody MovimientoStock movimiento) {

        MovimientoStock movimientoRegistrado = productoService.registrarMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
    }

    @GetMapping("/todos")
    public List<MovimientoStock> todosLosMovimiento() {
        return movimientoService.obtenerTodosLosMovimientos();
    }

    @GetMapping("/{id}")
    public List<MovimientoStock> movimientosProducto(@PathVariable Integer id) {
        return movimientoService.obtenerMovimientosProducto(id);
    }
}
