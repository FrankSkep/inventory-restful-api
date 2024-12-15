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
import java.util.stream.Collectors;

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
            throw new ProductNotFoundException("Producto no encontrado");
        }
        return movementRepository.findByProductId(id);
    }

    @Override
    public List<MovementResponse> getEntries() {
//        List<Movement> movements = movementRepository.findByTipoMovimiento(Movement.TipoMovimiento.ENTRADA);
//        return movements.stream().map(movementMapperDTO::toDTO).collect(Collectors.toList());
        return movementRepository.findByTipoMovimiento(Movement.TipoMovimiento.ENTRADA);
    }

    @Override
    public List<MovementResponse> getOutputs() {
//        List<Movement> movements = movementRepository.findByTipoMovimiento(Movement.TipoMovimiento.SALIDA);
//        return movements.stream().map(movementMapperDTO::toDTO).collect(Collectors.toList());
        return movementRepository.findByTipoMovimiento(Movement.TipoMovimiento.SALIDA);
    }

    @Override
    public List<MovementResponse> getAll() {
        List<Movement> movements = movementRepository.findAll();
        return movements.stream().map(movementMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAllEntries() {
        movementRepository.deleteByTipoMovimiento(Movement.TipoMovimiento.ENTRADA);
    }

    @Override
    public void delete(Long id) {
        Movement movementDB = movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException("Movimiento no encontrado"));
        movementRepository.delete(movementDB);
    }

    @Override
    public void deleteAllOutputs() {
        movementRepository.deleteByTipoMovimiento(Movement.TipoMovimiento.SALIDA);
    }

    @Override
    public void deleteAllMovements() {
        movementRepository.deleteAll();
    }
}
