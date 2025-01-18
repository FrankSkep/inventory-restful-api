package com.fran.inventory_api.system.service.Impl;

import com.fran.inventory_api.system.dto.ProductResponseSupplier;
import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.dto.SupplierResponseDetailed;
import com.fran.inventory_api.system.entity.Supplier;
import com.fran.inventory_api.system.exception.SupplierNotFoundException;
import com.fran.inventory_api.system.mapper.SupplierMapper;
import com.fran.inventory_api.system.repository.SupplierRepository;
import com.fran.inventory_api.system.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper proveedorMapper) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<SupplierResponseBasic> getAllSuppliers() {
        return supplierRepository.findAllBasic();
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {

        Supplier supplierDB = getById(supplier.getId());

        supplierDB.setName(supplier.getName());
        supplierDB.setAddress(supplier.getAddress());
        supplierDB.setEmail(supplier.getEmail());
        supplierDB.setPhone(supplier.getPhone());
        supplierDB.setTaxIdentification(supplier.getTaxIdentification());

        return supplierRepository.save(supplierDB);
    }

    @Override
    public void deleteSupplier(Long id) {
        Supplier supplier = getById(id);

        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier getById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier with id " + id + " not found"));
    }

    @Override
    public SupplierResponseDetailed getDetails(Long id) {
        Supplier supplier = getById(id);

        List<ProductResponseSupplier> products = supplierRepository.findProductsBySupplierId(id);
        return new SupplierResponseDetailed(
                supplier.getId(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getTaxIdentification(),
                new HashSet<>(products)
        );
    }
}
