package com.fran.inventory_api.application.service;

import com.fran.inventory_api.application.dto.SupplierResponseBasic;
import com.fran.inventory_api.application.dto.SupplierResponseDetailed;
import com.fran.inventory_api.application.entity.Supplier;

import java.util.List;

public interface SupplierService {

    List<SupplierResponseBasic> getAllSuppliers();

    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier);

    Supplier getById(Long id);

    void deleteSupplier(Long id);

    SupplierResponseDetailed getDetails(Long id);
}
