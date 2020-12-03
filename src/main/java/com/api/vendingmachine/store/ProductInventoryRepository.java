package com.api.vendingmachine.store;

import com.api.vendingmachine.models.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
}
