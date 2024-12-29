package com.fran.inventory_api.application.dto;

import com.fran.inventory_api.application.entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MovementResponse {
    private Long id;
    private Movement.MovementType movementType;
    private LocalDateTime date;
    private String reason;
    private Long quantity;
    private ProductResponseBasic product;
    private Double acquisitionCost;
}
