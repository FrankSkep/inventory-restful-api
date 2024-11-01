package com.fran.InventoryAPI.service.Impl;

import com.fran.InventoryAPI.dto.MovimientoResponse;
import com.fran.InventoryAPI.entity.MovimientoStock;
import com.fran.InventoryAPI.dto_mapper.MovimientoMapperDTO;
import com.fran.InventoryAPI.repository.MovimientoStockRepository;
import com.fran.InventoryAPI.repository.ProductoRepository;
import com.fran.InventoryAPI.service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoStockRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoMapperDTO movimientoMapperDTO;

    public MovimientoServiceImpl(MovimientoStockRepository movimientoRepository, ProductoRepository productoRepository,
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
}
