package com.fran.inventory_api.controller;

import com.fran.inventory_api.entity.Notificacion;
import com.fran.inventory_api.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/no-leidas")
    public List<Notificacion> getUnreadNotifications() {
        return notificacionService.getUnread();
    }

    @GetMapping("/todas")
    public List<Notificacion> getAllNotifications() {
        return notificacionService.getAll();
    }

    @PostMapping("/marcar-leida/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificacionService.readed(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación marcada como leída");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificacionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación eliminada");
    }

    @DeleteMapping("/eliminar-todas")
    public ResponseEntity<?> deleteAllNotifications() {
        notificacionService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Notificaciones eliminadas");
    }
}
