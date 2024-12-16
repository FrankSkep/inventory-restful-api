package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.entity.Movement;
import com.fran.inventory_api.exception.MovementNotFoundException;
import com.fran.inventory_api.exception.ProductNotFoundException;
import com.fran.inventory_api.mapper.MovementMapperDTO;
import com.fran.inventory_api.repository.MovementRepository;
import com.fran.inventory_api.repository.ProductRepository;
import com.fran.inventory_api.service.MovementService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service

public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final MovementMapperDTO movementMapperDTO;

    public MovementServiceImpl(MovementRepository movementRepository, ProductRepository productRepository, MovementMapperDTO movementMapperDTO) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.movementMapperDTO = movementMapperDTO;
    }

    @Override
    public List<Movement> getByProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        return movementRepository.findByProductId(id);
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
    public List<MovementResponse> getAll() {
        return movementRepository.findAllBasic();
//        List<Movement> movements = movementRepository.findAll();
//        return movements.stream().map(movementMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAllEntries() {
        movementRepository.deleteByType(Movement.MovementType.ENTRY);
    }

    @Override
    public void delete(Long id) {
        Movement movementDB = movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException("Movement not found"));
        movementRepository.delete(movementDB);
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
