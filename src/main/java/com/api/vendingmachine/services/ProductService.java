package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Product;
import com.api.vendingmachine.models.ProductInventory;
import com.api.vendingmachine.store.ProductInventoryRepository;
import com.api.vendingmachine.store.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    public ProductService() {}

    public Product getProduct(int productId) throws Exception {

        Optional<Product> product = this.productRepository.findById(productId);

        if(product.isEmpty()) {
            throw new Exception("Invalid product id");
        }

        Optional<ProductInventory> productInventory = this.productInventoryRepository.findById(productId);

        if (productInventory.isEmpty() || productInventory.get().getCount() == 0) {
            throw new Exception("Product out of stock: " + product);
        }

        this.reduceInventory(productInventory.get());
        return product.get();
    }

    private void reduceInventory(ProductInventory productInventory) {
        int numberOfProducts = productInventory.getCount();
        this.productInventoryRepository.save(new ProductInventory(productInventory.getProductId(), --numberOfProducts));
    }

}
