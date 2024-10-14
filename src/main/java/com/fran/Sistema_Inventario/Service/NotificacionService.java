package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Notificacion;

import java.util.List;

public interface NotificacionService {
    void sendNotification(String message);

    List<Notificacion> getUnreadNotifications();

    List<Notificacion> getAllNotifications();

    void readed(Long id);

    void deleteNotification(Long id);

    void deleteAllNotifications();
}
