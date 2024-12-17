package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.entity.Notification;
import com.fran.inventory_api.exception.NotificationNotFoundException;
import com.fran.inventory_api.repository.NotificationRepository;
import com.fran.inventory_api.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(String message) {
        Notification notification = new Notification(message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnread() {
        return notificationRepository.findByisReadFalse();
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void readed(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notificación no encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void delete(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notificación no encontrada"));
        notificationRepository.delete(notification);
    }

    @Override
    public void deleteAll() {
        notificationRepository.deleteAll();
    }
}
