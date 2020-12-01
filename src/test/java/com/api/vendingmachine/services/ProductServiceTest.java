package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Product;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest {

    @Test
    public void getProductTest() throws Exception {

        ProductService productService = new ProductService();
        Product product = productService.getProduct(1);
        assertEquals(new Product(1, 10, "Chips"), product);

    }

    @Test
    public void getProductInsufficientTest() throws Exception {

        ProductService productService = new ProductService();
        for (int i = 0; i < 10; i++) {
            productService.getProduct(1);
        }

        assertThrows(Exception.class, () -> productService.getProduct(1));

    }
}
