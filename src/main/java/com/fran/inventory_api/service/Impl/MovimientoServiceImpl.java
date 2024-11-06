package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.MovimientoResponse;
import com.fran.inventory_api.entity.Movimiento;
import com.fran.inventory_api.exception.MovementNotFoundException;
import com.fran.inventory_api.exception.ProductNotFoundException;
import com.fran.inventory_api.mapper.MovimientoMapperDTO;
import com.fran.inventory_api.repository.MovimientoRepository;
import com.fran.inventory_api.repository.ProductoRepository;
import com.fran.inventory_api.service.MovimientoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoMapperDTO movimientoMapperDTO;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, ProductoRepository productoRepository,
                                 MovimientoMapperDTO movimientoMapperDTO) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
        this.movimientoMapperDTO = movimientoMapperDTO;
    }

    @Override
    public List<Movimiento> getByProduct(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductNotFoundException("Producto no encontrado");
        }
        return movimientoRepository.findByProductoId(id);
    }

    @Override
    public List<MovimientoResponse> getEntries() {
        List<Movimiento> movimientos = movimientoRepository.findByTipoMovimiento(Movimiento.TipoMovimiento.ENTRADA);
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovimientoResponse> getOutputs() {
        List<Movimiento> movimientos = movimientoRepository.findByTipoMovimiento(Movimiento.TipoMovimiento.SALIDA);
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovimientoResponse> getAll() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAllEntries() {
        movimientoRepository.deleteByTipoMovimiento(Movimiento.TipoMovimiento.ENTRADA);
    }

    @Override
    public void delete(Long id) {
        Movimiento movementDB = movimientoRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException("Movimiento no encontrado"));
        movimientoRepository.delete(movementDB);
    }

    @Override
    public void deleteAllOutputs() {
        movimientoRepository.deleteByTipoMovimiento(Movimiento.TipoMovimiento.SALIDA);
    }

    @Override
    public void deleteAllMovements() {
        movimientoRepository.deleteAll();
    }
}
