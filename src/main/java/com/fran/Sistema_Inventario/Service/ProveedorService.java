package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import java.util.List;

public interface ProveedorService {

    List<ProveedorBasicoDTO> obtenerProveedores();

    Proveedor registrarProveedor(Proveedor proveedor);

    Proveedor editarProveedor(Proveedor proveedor);

    Proveedor obtenerPorID(Long id);

    void delete(Long id);

    ProveedorDetalladoDTO detallesProveedor(Proveedor provedor);
}
