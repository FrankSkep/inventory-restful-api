package com.fran.Sistema_Inventario.Controller;

import com.fran.Sistema_Inventario.Entity.Notificacion;
import com.fran.Sistema_Inventario.Service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/no-leidas")
    public List<Notificacion> getUnreadNotifications() {
        return notificacionService.getUnreadNotifications();
    }

    @GetMapping("/todas")
    public List<Notificacion> getAllNotifications() {
        return notificacionService.getAllNotifications();
    }

    @PostMapping("/marcar-leida/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificacionService.readed(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación marcada como leída");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificacionService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación eliminada");
    }

    @DeleteMapping("/eliminar-todas")
    public ResponseEntity<?> deleteAllNotifications() {
        notificacionService.deleteAllNotifications();
        return ResponseEntity.status(HttpStatus.OK).body("Notificaciones eliminadas");
    }
}
