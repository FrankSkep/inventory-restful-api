package com.fran.inventory_api.service;

import com.fran.inventory_api.dto.SupplierResponseBasic;
import com.fran.inventory_api.dto.SupplierResponseDetailed;
import com.fran.inventory_api.entity.Supplier;
import java.util.List;

public interface SupplierService {

    List<SupplierResponseBasic> getAll();

    Supplier save(Supplier supplier);

    Supplier update(Supplier supplier);

    Supplier getById(Long id);

    void delete(Long id);

    SupplierResponseDetailed getDetails(Long id);
}
