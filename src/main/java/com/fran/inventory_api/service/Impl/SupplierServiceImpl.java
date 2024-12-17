package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.ProductResponseSupplier;
import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import com.fran.inventory_api.exception.SupplierNotFoundException;
import com.fran.inventory_api.mapper.SupplierMapperDTO;
import com.fran.inventory_api.repository.SupplierRepository;
import com.fran.inventory_api.service.SupplierService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
        supplierDB.setPhone(supplier.getPhone());
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
        Optional<Supplier> supplierOpt = supplierRepository.findSupplierById(id);
        if (supplierOpt.isPresent()) {
            Supplier supplier = supplierOpt.get();
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
        } else {
            throw new RuntimeException("Supplier not found");
        }
//        return supplierRepository.getDetailsById(id);
    }
}
