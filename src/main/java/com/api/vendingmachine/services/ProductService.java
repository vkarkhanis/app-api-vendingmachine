package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductService {

    private Map<Integer, Product> products = new HashMap<>();
    private Map<Integer, Integer> productInventory = new HashMap<>();

    public ProductService() {

        products.put(1, new Product(1, 10, "Chips"));
        products.put(2, new Product(2, 30, "Soft Drink"));
        products.put(3, new Product(3, 20, "Biscuits"));
        products.put(4, new Product(4, 25, "Cream Biscuits"));
        products.put(5, new Product(5, 10, "Chocolate"));
        products.put(6, new Product(6, 20, "Tetra Pack"));

        productInventory.put(1, 10);
        productInventory.put(2, 40);
        productInventory.put(3, 40);
        productInventory.put(4, 30);
        productInventory.put(5, 50);
        productInventory.put(6, 30);

    }

    public Product getProduct(int productId) throws Exception {

        if (!isProductAvailable(productId)) {
            throw new Exception("Product out of stock");
        }

        Product product = this.products.get(productId);
        this.reduceInventory(productId);
        return product;
    }

    private void reduceInventory(int productId) {
        int numberOfProducts = this.productInventory.get(productId);
        this.productInventory.put(productId, --numberOfProducts);
    }

    private boolean isProductAvailable(int productId) {
        return this.productInventory.get(productId) != 0;
    }


}
