package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.entity.Notificacion;
import com.fran.inventory_api.repository.NotificacionRepository;
import com.fran.inventory_api.service.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void sendNotification(String message) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMessage(message);
        notificacion.setRead(false);
        notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> getUnread() {
        return notificacionRepository.findByisReadFalse();
    }

    public List<Notificacion> getAll() {
        return notificacionRepository.findAll();
    }

    @Override
    public void readed(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notificacion.setRead(true);
        notificacionRepository.save(notificacion);
    }

    @Override
    public void delete(Long id) {
        notificacionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        notificacionRepository.deleteAll();
    }
}
