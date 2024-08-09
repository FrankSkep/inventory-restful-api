package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import java.util.List;

public interface MovimientoService {

    public List<MovimientoStock> obtenerMovimientosProducto(Integer id);

    public List<MovimientoStock> obtenerTodosLosMovimientos();
}
