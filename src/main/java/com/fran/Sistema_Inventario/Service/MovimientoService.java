package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.MovimientoDTO;
import com.fran.Sistema_Inventario.Entity.MovimientoStock;

import java.util.List;

public interface MovimientoService {

    List<MovimientoStock> getByProduct(Long id);

    List<MovimientoDTO> getEntries();

    List<MovimientoDTO> getOutputs();

    List<MovimientoDTO> getAll();
}
