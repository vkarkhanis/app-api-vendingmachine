package com.api.vendingmachine.store;

import com.api.vendingmachine.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
