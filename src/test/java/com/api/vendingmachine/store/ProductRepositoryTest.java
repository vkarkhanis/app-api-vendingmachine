package com.api.vendingmachine.store;

import com.api.vendingmachine.models.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(1, 20, "Product 1");
    }

    @Test
    @Order(1)
    public void addProduct() {
        Product createProduct = productRepository.save(product);
        assertEquals(product, createProduct);
    }

    @Test
    @Order(2)
    public void getProduct() {
        Optional<Product> productSelect = productRepository.findById(1);
        assertEquals(product, productSelect.get());
    }

    @Test
    @Order(3)
    public void updateProduct() {

        Product updateProduct = new Product(1, 30, "Product 1");

        Product product = productRepository.save(updateProduct);
        assertEquals(updateProduct, product);
    }

    @Test
    @Order(4)
    public void findAllProducts() {

        List<Product> products = productRepository.findAll();
        assertEquals(1, products.size());
    }

    @Test
    @Order(5)
    public void deleteProduct() {

        productRepository.deleteById(1);
        assertEquals(Optional.empty(), productRepository.findById(1));
    }

}
