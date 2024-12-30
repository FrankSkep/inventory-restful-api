package com.fran.inventory_api.system.repository;

import com.fran.inventory_api.system.dto.MovementResponse;
import com.fran.inventory_api.system.entity.Movement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByProductId(Long id);

    @Query("SELECT new com.fran.inventory_api.system.dto.MovementResponse(m.id, m.type, m.date, m.reason, m.quantity, " +
            "new com.fran.inventory_api.system.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url), " +
            "m.acquisitionCost) " +
            "FROM Movement m JOIN m.product p")
    List<MovementResponse> findAllBasic();

    @Query("SELECT new com.fran.inventory_api.system.dto.MovementResponse(m.id, m.type, m.date, m.reason, m.quantity, " +
            "new com.fran.inventory_api.system.dto.ProductResponseBasic(p.id, p.name, p.description, p.price, p.stock, p.category.name, p.image.url), " +
            "m.acquisitionCost) " +
            "FROM Movement m JOIN m.product p " +
            "WHERE m.type = :movementType")
    List<MovementResponse> findByType(@Param("movementType") Movement.MovementType movementType);

    @Transactional
    void deleteByType(Movement.MovementType movementType);

    @Transactional
    void deleteAll();
}
