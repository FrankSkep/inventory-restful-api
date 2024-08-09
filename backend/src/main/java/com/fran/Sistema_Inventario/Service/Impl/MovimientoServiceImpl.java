package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import com.fran.Sistema_Inventario.Repository.MovimientoStockRepository;
import com.fran.Sistema_Inventario.Repository.ProductoRepository;
import com.fran.Sistema_Inventario.Service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    MovimientoStockRepository movimientoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public List<MovimientoStock> obtenerMovimientosProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado");
        }
        return movimientoRepository.findByProductoId(id);
    }

    @Override
    public List<MovimientoStock> obtenerTodosLosMovimientos() {
        return movimientoRepository.findAll();
    }
}
