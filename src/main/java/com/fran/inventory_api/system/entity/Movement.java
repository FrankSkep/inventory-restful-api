package com.fran.inventory_api.system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MovementType type;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "reason", nullable = false)
    private String reason;

    @NotNull(message = "The quantity is mandatory.")
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Product product;

    @Min(value = 0, message = "The acquisition cost must be greater than zero.")
    @Column(name = "acquisition_cost", nullable = false)
    private Double acquisitionCost;

    public enum MovementType {
        ENTRY, EXIT
    }

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }
}
