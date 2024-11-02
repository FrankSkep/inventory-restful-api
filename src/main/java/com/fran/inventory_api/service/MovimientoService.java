package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.MovimientoResponse;
import com.fran.inventory_api.entity.MovimientoStock;

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
