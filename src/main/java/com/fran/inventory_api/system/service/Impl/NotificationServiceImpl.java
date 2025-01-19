package com.fran.inventory_api.system.service.Impl;

import com.fran.inventory_api.system.entity.Notification;
import com.fran.inventory_api.system.exception.NotificationNotFoundException;
import com.fran.inventory_api.system.repository.NotificationRepository;
import com.fran.inventory_api.system.service.NotificationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
    }

    @Override
    public void sendNotification(String message) {
        Notification notification = new Notification(message);
        notificationRepository.save(notification);
    }

    @Override
    @Cacheable("unreadNotifications")
    public List<Notification> getUnread() {
        return notificationRepository.findByisReadFalse();
    }

    @Override
    @Cacheable("allNotifications")
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    @CacheEvict(value = {"unreadNotifications", "allNotifications"}, allEntries = true)
    public void readed(Long id) {
        Notification notification = getById(id);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    @CacheEvict(value = {"unreadNotifications", "allNotifications"}, allEntries = true)
    public void delete(Long id) {
        Notification notification = getById(id);
        notificationRepository.delete(notification);
    }

    @Override
    @CacheEvict(value = {"unreadNotifications", "allNotifications"}, allEntries = true)
    public void deleteAll() {
        notificationRepository.deleteAll();
    }
}