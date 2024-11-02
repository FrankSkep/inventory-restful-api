package com.fran.inventory_api.service;

import com.fran.inventory_api.entity.Notificacion;

import java.util.List;

public interface NotificacionService {
    void sendNotification(String message);

    List<Notificacion> getUnread();

    List<Notificacion> getAll();

    void readed(Long id);

    void delete(Long id);

    void deleteAll();
}
