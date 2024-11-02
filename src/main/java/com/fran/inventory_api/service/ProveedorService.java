package com.fran.InventoryAPI.service;

import com.fran.InventoryAPI.dto.Proveedor.ProveedorResponseBasic;
import com.fran.InventoryAPI.dto.Proveedor.ProveedorResponseDetailed;
import com.fran.InventoryAPI.entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseBasic> obtenerProveedores();

    Proveedor save(Proveedor proveedor);

    Proveedor update(Proveedor proveedor);

    Proveedor getById(Long id);

    void delete(Long id);

    ProveedorResponseDetailed getDetails(Proveedor provedor);
}
