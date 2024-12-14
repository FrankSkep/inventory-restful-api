package com.fran.inventory_api.controller;

import com.fran.inventory_api.entity.Notification;
import com.fran.inventory_api.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/no-leidas")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnread();
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAll();
    }

    @PostMapping("/{id}/marcar-leida")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationService.readed(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación marcada como leída");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación eliminada");
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAllNotifications() {
        notificationService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Notificaciones eliminadas");
    }
}
