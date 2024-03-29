package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Product;
import com.api.vendingmachine.models.ProductInventory;
import com.api.vendingmachine.store.ProductInventoryRepository;
import com.api.vendingmachine.store.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductInventoryRepository productInventoryRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void getProductTest() throws Exception {

        Product mockProduct = new Product(1, 20, "Product 1");
        ProductInventory mockProductInventory = new ProductInventory(1, 30);

        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(productInventoryRepository.findById(1)).thenReturn(Optional.of(mockProductInventory));

        Product product = productService.getProduct(1);
        assertEquals(mockProduct, product);

    }

    @Test
    public void getAllProductsTest() throws Exception {

        Product[] products = new Product[]{new Product(1, 30, "Product 1"), new Product(2, 42, "Product 2")};
        List<Product> allProducts = Arrays.asList(products);
        when(productRepository.findAll()).thenReturn(allProducts);

        List<Product> returnProducts = productService.getAllProducts();
        assertEquals(2, returnProducts.size());
    }

    @Test
    public void getProductInsufficientTest() {

        Product mockProduct = new Product(1, 20, "Product 1");
        ProductInventory mockProductInventory = new ProductInventory(1, 0);

        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(productInventoryRepository.findById(1)).thenReturn(Optional.of(mockProductInventory));

        assertThrows(Exception.class, () -> productService.getProduct(1));

    }

    @Test
    public void getProductInventoryMissingTest() {

        Product mockProduct = new Product(1, 20, "Product 1");

        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(productInventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.getProduct(1));

    }

    @Test
    public void getProductMissingProductTest() {

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.getProduct(1));

    }

}
