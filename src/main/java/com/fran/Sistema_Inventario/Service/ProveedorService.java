package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    public List<ProveedorBasicoDTO> obtenerProveedores();

    public Proveedor registrarProveedor(Proveedor proveedor);

    public Proveedor editarProveedor(Long id, ProveedorDTO proveedor);

    public Proveedor obtenerPorID(Long id);

    public boolean eliminarProveedor(Long id);

}
