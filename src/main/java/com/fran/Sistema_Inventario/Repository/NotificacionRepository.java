package com.fran.Sistema_Inventario.Repository;

import com.fran.Sistema_Inventario.Entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByisReadFalse();
}
