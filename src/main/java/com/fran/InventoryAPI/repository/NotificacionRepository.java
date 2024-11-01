package com.fran.InventoryAPI.repository;

import com.fran.InventoryAPI.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByisReadFalse();
}
