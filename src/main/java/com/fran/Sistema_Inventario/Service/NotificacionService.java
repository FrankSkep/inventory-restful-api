package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Notificacion;

import java.util.List;

public interface NotificacionService {
    void sendNotification(String message);

    List<Notificacion> getUnread();

    List<Notificacion> getAll();

    void readed(Long id);

    void delete(Long id);

    void deleteAll();
}
