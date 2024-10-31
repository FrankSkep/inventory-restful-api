package com.fran.InventoryAPI.Service;

import com.fran.InventoryAPI.DTO.MovimientoResponse;
import com.fran.InventoryAPI.Entity.MovimientoStock;

import java.util.List;

public interface MovimientoService {

    List<MovimientoStock> getByProduct(Long id);

    List<MovimientoResponse> getEntries();

    List<MovimientoResponse> getOutputs();

    List<MovimientoResponse> getAll();
}
