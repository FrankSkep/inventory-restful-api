package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.dto.MovementResponse;
import com.fran.inventory_api.system.entity.Movement;

import java.util.List;

public interface MovementService {

    List<MovementResponse> getAllMovements();

    List<MovementResponse> getEntries();

    List<MovementResponse> getOutputs();

    Movement createMovement(Movement movement);

    void deleteMovement(Long id);

    void deleteAllEntries();

    void deleteAllOutputs();

    void deleteAllMovements();
}
