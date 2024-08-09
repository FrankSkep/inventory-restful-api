package com.fran.Sistema_Inventario.Repository;

import com.fran.Sistema_Inventario.Entity.MovimientoStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Integer> {

    List<MovimientoStock> findByProductoId(Integer id);
}
