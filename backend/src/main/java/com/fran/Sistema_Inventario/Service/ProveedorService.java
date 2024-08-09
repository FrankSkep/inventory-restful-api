package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProveedorDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {
    
    public List<Proveedor> obtenerProveedores();

    public Proveedor registrarProveedor(Proveedor proveedor);
    
    public Proveedor editarProveedor(Integer id, ProveedorDTO proveedor);

    public Proveedor obtenerPorID(Integer id);

}
