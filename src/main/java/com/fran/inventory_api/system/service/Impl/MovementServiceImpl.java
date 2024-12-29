package com.fran.inventory_api.system.service.Impl;

import com.fran.inventory_api.system.dto.MovementResponse;
import com.fran.inventory_api.system.entity.Movement;
import com.fran.inventory_api.system.exception.MovementNotFoundException;
import com.fran.inventory_api.system.repository.MovementRepository;
import com.fran.inventory_api.system.service.MovementService;
import com.fran.inventory_api.system.service.ProductService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final ProductService productService;

    public MovementServiceImpl(MovementRepository movementRepository, ProductService productService) {
        this.movementRepository = movementRepository;
        this.productService = productService;
    }

    @Override
    public List<MovementResponse> getAllMovements() {
        return movementRepository.findAllBasic();
    }

    @Override
    public List<MovementResponse> getEntries() {
        return movementRepository.findByType(Movement.MovementType.ENTRY);
    }

    @Override
    public List<MovementResponse> getOutputs() {
        return movementRepository.findByType(Movement.MovementType.EXIT);
    }

    @Override
    public Movement createMovement(Movement movement) {
        Movement createdMovement = productService.updateStock(movement);
        return movementRepository.save(createdMovement);
    }

    @Override
    public void deleteMovement(Long id) {
        Movement movementDB = movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found"));
        movementRepository.delete(movementDB);
    }

    @Override
    public void deleteAllEntries() {
        movementRepository.deleteByType(Movement.MovementType.ENTRY);
    }

    @Override
    public void deleteAllOutputs() {
        movementRepository.deleteByType(Movement.MovementType.EXIT);
    }

    @Override
    public void deleteAllMovements() {
        movementRepository.deleteAll();
    }
}
