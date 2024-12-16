package com.fran.inventory_api.mapper;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.entity.Movement;
import org.springframework.stereotype.Component;

@Component
public class MovementMapperDTO {

    private final ProductMapperDTO productoMapper;

    public MovementMapperDTO(ProductMapperDTO productoMapper) {
        this.productoMapper = productoMapper;
    }

    public MovementResponse toDTO(Movement movement) {
        return new MovementResponse(
                movement.getId(),
                movement.getType(),
                movement.getDate(),
                movement.getReason(),
                movement.getQuantity(),
                productoMapper.toDTObasic(movement.getProduct()),
                movement.getAcquisitionCost()
        );
    }

}
