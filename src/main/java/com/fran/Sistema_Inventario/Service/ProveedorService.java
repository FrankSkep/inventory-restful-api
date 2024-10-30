package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.App.Proveedor.ProveedorResponseBasic;
import com.fran.Sistema_Inventario.DTO.App.Proveedor.ProveedorResponseDetailed;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseBasic> obtenerProveedores();

    Proveedor save(Proveedor proveedor);

    Proveedor update(Proveedor proveedor);

    Proveedor getById(Long id);

    void delete(Long id);

    ProveedorResponseDetailed getDetails(Proveedor provedor);
}
