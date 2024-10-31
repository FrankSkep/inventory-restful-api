package com.fran.InventoryAPI.Repository;

import com.fran.InventoryAPI.Entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByisReadFalse();
}
