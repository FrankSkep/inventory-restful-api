package com.fran.Sistema_Inventario.Service.Impl;

import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorBasicoDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDTO;
import com.fran.Sistema_Inventario.DTO.ProveedorDTOs.ProveedorDetalladoDTO;
import com.fran.Sistema_Inventario.Entity.Proveedor;
import com.fran.Sistema_Inventario.MapperDTO.ProveedorMapperDTO;
import com.fran.Sistema_Inventario.Repository.ProveedorRepository;
import com.fran.Sistema_Inventario.Service.ProveedorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorMapperDTO proveedorMapper;

    @Override
    public List<ProveedorBasicoDTO> obtenerProveedores() {
        // ProveedorServiceImpl::convertirDTOrespuesta
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream().map(proveedorMapper::toDTObasic).collect(Collectors.toList());
    }

    @Override
    public Proveedor obtenerPorID(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
    }
    
    public ProveedorDetalladoDTO detallesProveedor(Proveedor provedor) {
        return proveedorMapper.toDTOdetailed(provedor);
    }

    @Override
    public Proveedor registrarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor editarProveedor(Long id, ProveedorDTO proveedor) {

        Proveedor proveedorDB = proveedorRepository.getReferenceById(id);

        proveedorDB.setNombre(proveedor.getNombre());
        proveedorDB.setDireccion(proveedor.getDireccion());
        proveedorDB.setEmail(proveedor.getEmail());
        proveedorDB.setTelefono(proveedor.getTelefono());
        proveedorDB.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());

        return proveedorRepository.save(proveedorDB);
    }

    @Override
    public boolean eliminarProveedor(Long id) {

        Proveedor proveedor = proveedorRepository.getReferenceById(id);

        if (proveedor == null) {
            return false;
        }

        proveedorRepository.delete(proveedor);
        return true;
    }
}
