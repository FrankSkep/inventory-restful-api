package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.MovimientoResponse;
import com.fran.inventory_api.entity.MovimientoStock;
import com.fran.inventory_api.mapper.MovimientoMapperDTO;
import com.fran.inventory_api.repository.MovimientoRepository;
import com.fran.inventory_api.repository.ProductoRepository;
import com.fran.inventory_api.service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;

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
    public List<MovimientoStock> getByProduct(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado");
        }
        return movimientoRepository.findByProductoId(id);
    }

    @Override
    public List<MovimientoResponse> getEntries() {
        List<MovimientoStock> movimientos = movimientoRepository.findByTipoMovimiento(MovimientoStock.TipoMovimiento.ENTRADA);
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovimientoResponse> getOutputs() {
        List<MovimientoStock> movimientos = movimientoRepository.findByTipoMovimiento(MovimientoStock.TipoMovimiento.SALIDA);
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovimientoResponse> getAll() {
        List<MovimientoStock> movimientos = movimientoRepository.findAll();
        return movimientos.stream().map(movimientoMapperDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAllEntries() {
        movimientoRepository.deleteByTipoMovimiento(MovimientoStock.TipoMovimiento.ENTRADA);
    }

    @Override
    public void deleteAllOutputs() {
        movimientoRepository.deleteByTipoMovimiento(MovimientoStock.TipoMovimiento.SALIDA);
    }

    @Override
    public void deleteAllMovements() {
        movimientoRepository.deleteAll();
    }
}
