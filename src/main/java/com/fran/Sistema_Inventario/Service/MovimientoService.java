package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;

import java.util.List;

public interface MovimientoService {

    List<MovimientoStock> obtenerMovimientosProducto(Long id);

    List<MovimientoStock> obtenerEntradas();

    List<MovimientoStock> obtenerSalidas();

    List<MovimientoStock> obtenerTodosLosMovimientos();
}
