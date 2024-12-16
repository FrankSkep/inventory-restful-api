package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import com.fran.inventory_api.exception.SupplierNotFoundException;
import com.fran.inventory_api.mapper.SupplierMapperDTO;
import com.fran.inventory_api.repository.SupplierRepository;
import com.fran.inventory_api.service.SupplierService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapperDTO proveedorMapper) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<SupplierResponseBasic> getAll() {
        return supplierRepository.findAllBasic();
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier supplier) {

        Supplier supplierDB = supplierRepository.getReferenceById(supplier.getId());

        supplierDB.setName(supplier.getName());
        supplierDB.setAddress(supplier.getAddress());
        supplierDB.setEmail(supplier.getEmail());
        supplierDB.setPhoneNumber(supplier.getPhoneNumber());
        supplierDB.setTaxIdentification(supplier.getTaxIdentification());

        return supplierRepository.save(supplierDB);
    }

    @Override
    public void delete(Long id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Proveedor con id " + id + " no encontrado"));

        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier getById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Proveedor con id " + id + " no encontrado"));
    }

    @Override
    public SupplierResponseDetailed getDetails(Long id) {
        return supplierRepository.getDetailsById(id);
    }

}
