package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByisReadFalse();
}
