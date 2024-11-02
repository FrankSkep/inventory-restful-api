package com.fran.InventoryAPI.service;

import com.fran.InventoryAPI.dto.MovimientoResponse;
import com.fran.InventoryAPI.entity.MovimientoStock;

import java.util.List;

public interface MovimientoService {

    List<MovimientoStock> getByProduct(Long id);

    List<MovimientoResponse> getEntries();

    List<MovimientoResponse> getOutputs();

    List<MovimientoResponse> getAll();

    void deleteAllEntries();

    void deleteAllOutputs();

    void deleteAllMovements();
}
