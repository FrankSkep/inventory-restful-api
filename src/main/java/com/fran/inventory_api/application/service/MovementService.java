package com.fran.inventory_api.application.service;

import com.fran.inventory_api.application.entity.Movement;
import com.fran.inventory_api.application.dto.MovementResponse;

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
