package com.fran.InventoryAPI.Service.Impl;

import com.fran.InventoryAPI.DTO.MovimientoResponse;
import com.fran.InventoryAPI.Entity.MovimientoStock;
import com.fran.InventoryAPI.MapperDTO.MovimientoMapperDTO;
import com.fran.InventoryAPI.Repository.MovimientoStockRepository;
import com.fran.InventoryAPI.Repository.ProductoRepository;
import com.fran.InventoryAPI.Service.MovimientoService;
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
