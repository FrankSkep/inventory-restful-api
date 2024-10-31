package com.fran.InventoryAPI.Repository;

import com.fran.InventoryAPI.Entity.MovimientoStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {

    List<MovimientoStock> findByProductoId(Long id);
    List<MovimientoStock> findByTipoMovimiento(MovimientoStock.TipoMovimiento tipoMovimiento);
}
