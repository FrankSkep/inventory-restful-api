package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.Repository.ProveedorRepository;
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
}
