package com.fran.inventory_api.repository;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.entity.Movement;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByProductId(Long id);

//    List<Movement> findByTipoMovimiento(Movement.TipoMovimiento tipoMovimiento);

    @Query("SELECT new com.fran.inventory_api.dto.MovementResponse(m.id, m.type, m.date, m.reason, m.quantity, " +
            "new com.fran.inventory_api.dto.ProductResponseBasic(p.id, p.nombre, p.descripcion, p.precio, p.cantidadStock, p.category.nombre, p.image.url), " +
            "m.acquisitionCost) " +
            "FROM Movement m JOIN m.product p " +
            "WHERE m.type = :tipoMovimiento")
    List<MovementResponse> findByTipoMovimiento(@Param("tipoMovimiento") Movement.MovementType movementType);

    @Transactional
    void deleteByTipoMovimiento(Movement.MovementType movementType);

    @Transactional
    void deleteAll();
}
