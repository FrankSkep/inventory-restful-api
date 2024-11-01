package com.fran.InventoryAPI.repository;

import com.fran.InventoryAPI.entity.MovimientoStock;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<MovimientoStock, Integer> {

    List<MovimientoStock> findByProductoId(Long id);
    List<MovimientoStock> findByTipoMovimiento(MovimientoStock.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteByTipoMovimiento(MovimientoStock.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteAll();
}
