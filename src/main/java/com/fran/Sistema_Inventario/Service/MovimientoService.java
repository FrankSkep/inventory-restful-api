package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.App.MovimientoResponse;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;

import java.util.List;

public interface MovimientoService {

    List<MovimientoStock> getByProduct(Long id);

    List<MovimientoResponse> getEntries();

    List<MovimientoResponse> getOutputs();

    List<MovimientoResponse> getAll();
}
