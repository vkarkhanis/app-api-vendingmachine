package com.api.vendingmachine.store;

import com.api.vendingmachine.models.ProductInventory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductInventoryRepositoryTest {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    private ProductInventory productInventory;

    @BeforeEach
    public void setUp() {
        productInventory = new ProductInventory(1, 10);
    }

    @Test
    @Order(1)
    public void addProductInventory() {

        ProductInventory newEntity = productInventoryRepository.save(productInventory);
        assertEquals(productInventory, newEntity);
    }

    @Test
    @Order(2)
    public void getProductInventory() {

        Optional<ProductInventory> entity = productInventoryRepository.findById(1);
        assertEquals(productInventory, entity.get());
    }

    @Test
    @Order(3)
    public void upateProductInventory() {

        ProductInventory updatedEntity = new ProductInventory(1, 20);
        ProductInventory newEntity = productInventoryRepository.save(updatedEntity);

        assertEquals(newEntity, updatedEntity);
    }

    @Test
    @Order(4)
    public void removeProductInventory() {

        productInventoryRepository.deleteById(1);
        assertFalse(productInventoryRepository.findById(1).isPresent());
    }
}
