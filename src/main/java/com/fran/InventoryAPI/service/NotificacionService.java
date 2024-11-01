package com.fran.InventoryAPI.service;

import com.fran.InventoryAPI.entity.Notificacion;

import java.util.List;

public interface NotificacionService {
    void sendNotification(String message);

    List<Notificacion> getUnread();

    List<Notificacion> getAll();

    void readed(Long id);

    void delete(Long id);

    void deleteAll();
}
