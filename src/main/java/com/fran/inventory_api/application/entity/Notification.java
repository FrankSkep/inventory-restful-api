package com.fran.inventory_api.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }

    public Notification(String message) {
        this.message = message;
        this.isRead = false;
    }
}