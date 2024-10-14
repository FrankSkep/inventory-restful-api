package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.Proveedor.ProveedorResponseBasic;
import com.fran.Sistema_Inventario.DTO.Proveedor.ProveedorResponseDetailed;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorResponseBasic> obtenerProveedores();

    Proveedor registrarProveedor(Proveedor proveedor);

    Proveedor editarProveedor(Proveedor proveedor);

    Proveedor obtenerPorID(Long id);

    void delete(Long id);

    ProveedorResponseDetailed detallesProveedor(Proveedor provedor);
}
