package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Movimiento;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByProductoId(Long id);
    List<Movimiento> findByTipoMovimiento(Movimiento.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteByTipoMovimiento(Movimiento.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteAll();
}
