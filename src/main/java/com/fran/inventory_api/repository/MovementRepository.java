package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Movement;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByProductoId(Long id);
    List<Movement> findByTipoMovimiento(Movement.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteByTipoMovimiento(Movement.TipoMovimiento tipoMovimiento);

    @Transactional
    void deleteAll();
}
