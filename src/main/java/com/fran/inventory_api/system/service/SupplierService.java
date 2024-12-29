package com.fran.inventory_api.system.service;

import com.fran.inventory_api.system.dto.SupplierResponseBasic;
import com.fran.inventory_api.system.dto.SupplierResponseDetailed;
import com.fran.inventory_api.system.entity.Supplier;

import java.util.List;

public interface SupplierService {

    List<SupplierResponseBasic> getAllSuppliers();

    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier);

    Supplier getById(Long id);

    void deleteSupplier(Long id);

    SupplierResponseDetailed getDetails(Long id);
}
