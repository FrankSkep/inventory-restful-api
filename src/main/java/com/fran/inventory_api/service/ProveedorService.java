package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.Proveedor.ProveedorResponseBasic;
import com.fran.inventory_api.dto.Proveedor.ProveedorResponseDetailed;
import com.fran.inventory_api.entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseBasic> obtenerProveedores();

    Proveedor save(Proveedor proveedor);

    Proveedor update(Proveedor proveedor);

    Proveedor getById(Long id);

    void delete(Long id);

    ProveedorResponseDetailed getDetails(Proveedor provedor);
}
