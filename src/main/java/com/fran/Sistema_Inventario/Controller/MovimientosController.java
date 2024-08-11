package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Service.MovimientoService;
import com.fran.Sistema_Inventario.Service.Impl.ProductoServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class MovimientosController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private ProductoServiceImpl productoService;

    // Obtener todos los movimientos
    @GetMapping("/")
    public List<MovimientoStock> todosLosMovimientos() {
        return movimientoService.obtenerTodosLosMovimientos();
    }

    // Registrar nuevo movimiento de stock
    @PostMapping("/entrada")
    public ResponseEntity<MovimientoStock> entradaStock(@RequestBody MovimientoStock movimiento) {

        movimiento.setTipoMovimiento(MovimientoStock.TipoMovimiento.ENTRADA);
        MovimientoStock movimientoRegistrado = productoService.actualizarStock(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
    }

    // Registrar nuevo movimiento de stock
    @PostMapping("/salida")
    public ResponseEntity<MovimientoStock> salidaStock(@RequestBody MovimientoStock movimiento) {

        movimiento.setTipoMovimiento(MovimientoStock.TipoMovimiento.SALIDA);
        MovimientoStock movimientoRegistrado = productoService.actualizarStock(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoRegistrado);
    }
}
