package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.entity.Movement;

import java.util.List;

public interface MovementService {

    List<Movement> getByProduct(Long id);

    List<MovementResponse> getEntries();

    List<MovementResponse> getOutputs();

    List<MovementResponse> getAll();

    void deleteAllEntries();

    void delete(Long id);

    void deleteAllOutputs();

    void deleteAllMovements();
}
