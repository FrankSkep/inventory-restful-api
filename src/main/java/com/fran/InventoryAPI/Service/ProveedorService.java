package com.fran.InventoryAPI.Service;

import com.fran.InventoryAPI.DTO.Proveedor.ProveedorResponseBasic;
import com.fran.InventoryAPI.DTO.Proveedor.ProveedorResponseDetailed;
import com.fran.InventoryAPI.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseBasic> obtenerProveedores();

    Proveedor save(Proveedor proveedor);

    Proveedor update(Proveedor proveedor);

    Proveedor getById(Long id);

    void delete(Long id);

    ProveedorResponseDetailed getDetails(Proveedor provedor);
}
