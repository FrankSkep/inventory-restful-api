package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.entity.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotification(String message);

    List<Notification> getUnread();

    List<Notification> getAll();

    void readed(Long id);

    void delete(Long id);

    void deleteAll();
}
