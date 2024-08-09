package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProveedorDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.Repository.ProveedorRepository;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> obtenerProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor obtenerPorID(Integer id) {
        return proveedorRepository.getReferenceById(id);
    }

    @Override
    public Proveedor registrarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor editarProveedor(Integer id, ProveedorDTO proveedor) {

        Proveedor proveedorDB = proveedorRepository.getReferenceById(id);
        
        proveedorDB.setNombre(proveedor.getNombre());
        proveedorDB.setDireccion(proveedor.getDireccion());
        proveedorDB.setEmail(proveedor.getEmail());
        proveedorDB.setTelefono(proveedor.getTelefono());
        proveedorDB.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());                

        return proveedorRepository.save(proveedorDB);
    }
}
