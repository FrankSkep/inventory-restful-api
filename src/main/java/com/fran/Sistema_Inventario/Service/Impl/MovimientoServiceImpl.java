package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoStockRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoServiceImpl(MovimientoStockRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<MovimientoStock> obtenerMovimientosProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado");
        }
        return movimientoRepository.findByProductoId(id);
    }

    @Override
    public List<MovimientoStock> obtenerEntradas() {
        return movimientoRepository.findByTipoMovimiento(MovimientoStock.TipoMovimiento.ENTRADA);
    }

    @Override
    public List<MovimientoStock> obtenerSalidas() {
        return movimientoRepository.findByTipoMovimiento(MovimientoStock.TipoMovimiento.SALIDA);
    }

    @Override
    public List<MovimientoStock> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll();
    }
}
