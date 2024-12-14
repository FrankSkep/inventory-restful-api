package com.fran.inventory_api.repository;

import com.fran.inventory_api.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByisReadFalse();
}
