package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import java.util.List;

public interface SupplierService {

    List<SupplierResponseBasic> getAllSuppliers();

    Supplier createSupplier(Supplier supplier);

    Supplier updateSupplier(Supplier supplier);

    Supplier getById(Long id);

    void deleteSupplier(Long id);

    SupplierResponseDetailed getDetails(Long id);
}
