package com.fran.inventory_api.dto;

import com.fran.inventory_api.entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
