package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.MovimientoResponse;
import com.fran.inventory_api.entity.Movimiento;

import java.util.List;

public interface MovimientoService {

    List<Movimiento> getByProduct(Long id);

    List<MovimientoResponse> getEntries();

    List<MovimientoResponse> getOutputs();

    List<MovimientoResponse> getAll();

    void deleteAllEntries();

    void delete(Long id);

    void deleteAllOutputs();

    void deleteAllMovements();
}
