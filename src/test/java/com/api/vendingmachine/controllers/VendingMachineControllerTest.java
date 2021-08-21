package com.api.vendingmachine.controllers;

import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Product;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.models.VendingMachineOutput;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.OperationStatus;
import util.Status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendingMachineControllerTest {

    @Mock
    private MoneyService moneyService;

    @Mock
    private RequestService requestService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private VendingMachineController vendingMachineController;

    private Request request;

    @BeforeEach
    public void setUp() {

        request = new Request();
        request.setBalance(10);
        request.setStatus(Status.MONEY_ADDED);
    }

    @Test
    public void addMoneyTest() throws Exception {

        VendingMachineOutput expectedOutput = new VendingMachineOutput(0, null,
                new Amount(10), new Amount(0),
                OperationStatus.MONEY_ADDED);

        when(moneyService.addMoney(new Amount(10))).thenReturn(request);

        VendingMachineOutput actualOutput = vendingMachineController.amount(new Amount(10));
        assertEquals(expectedOutput, actualOutput);


    }

    @Test
    public void addMoneyFailureTest() throws Exception {

        when(moneyService.addMoney(new Amount(10))).thenReturn(null);

        assertThrows(Exception.class, ()-> vendingMachineController.amount(new Amount(10), 0));
    }

    @Test
    public void updateMoneyTest() throws Exception {

        VendingMachineOutput expectedOutput = new VendingMachineOutput(0, null,
                new Amount(10), new Amount(0),
                OperationStatus.MONEY_UPDATED);

        when(moneyService.updateMoney(new Amount(10), 1)).thenReturn(request);

        VendingMachineOutput actualOutput = vendingMachineController.amount(new Amount(10), 1);
        assertEquals(expectedOutput, actualOutput);


    }

    @Test
    public void updateMoneyFailureTest() throws Exception {

        when(moneyService.updateMoney(new Amount(10), 1)).thenReturn(null);

        assertThrows(Exception.class, ()-> vendingMachineController.amount(new Amount(10), 1));
    }


    @Test
    public void refundTest() throws Exception {

        VendingMachineOutput expectedOutput = new VendingMachineOutput(0, null,
                new Amount(0), new Amount(10),
                OperationStatus.REFUND_PROCESSED);

        Request req = new Request();
        req.setStatus(Status.MONEY_ADDED);
        req.setBalance(10);

        when(requestService.getPendingRequestById(0)).thenReturn(req);

        VendingMachineOutput actualOutput = vendingMachineController.refund(0);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void refundFailureTest() {

        when(requestService.getPendingRequestById(0)).thenReturn(null);

        assertThrows(Exception.class, () -> vendingMachineController.refund(0));
    }

    @Test
    public void getProductIncorrectRequestIdTest() {

        assertThrows(Exception.class, () -> vendingMachineController.product(1, 1));
    }

    @Test
    public void getProductIncorrectProductPriceTest() throws Exception {

        Product product = new Product(1, 0, "Product");

        when(productService.getProduct(1)).thenReturn(product);
        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);

        assertThrows(Exception.class, () -> vendingMachineController.product(1, 1));
    }

    @Test
    public void getProductInsufficientBalanceTest() throws Exception {

        Product product = new Product(1, 20, "Product");

        VendingMachineOutput expectedOp = new VendingMachineOutput(1, null,
                new Amount(request.getBalance()), new Amount(0),OperationStatus.INSUFFICIENT_BALANCE);

        when(productService.getProduct(1)).thenReturn(product);
        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);

        VendingMachineOutput actualOp = vendingMachineController.product(1, 1);
        assertEquals(expectedOp, actualOp);
    }

    @Test
    public void getProductTest() throws Exception {

        Product product = new Product(1, 5, "Product");
        VendingMachineOutput expectedOutput = new VendingMachineOutput(1,
                product, new Amount(0),new Amount(5),OperationStatus.PRODUCT_DISPATCHED);

        when(productService.getProduct(1)).thenReturn(product);
        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);

        VendingMachineOutput actualOutput = vendingMachineController.product(1, 1);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void getProductMultipleRequestTest() throws Exception {

        Product product = new Product(1, 15, "Product");
        Product product2 = new Product(2, 5, "Product");

        VendingMachineOutput expectedOutput = new VendingMachineOutput(1,
                product2, new Amount(0),new Amount(5),OperationStatus.PRODUCT_DISPATCHED);

        VendingMachineOutput output1 = new VendingMachineOutput(1, null,
                new Amount(10), new Amount(0),OperationStatus.INSUFFICIENT_BALANCE);

        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);
        when(productService.getProduct(1)).thenReturn(product);

        VendingMachineOutput actualOutput1 = vendingMachineController.product(1, 1);

        assertEquals(output1, actualOutput1);


        when(productService.getProduct(2)).thenReturn(product2);
        VendingMachineOutput actualOutput2 = vendingMachineController.product(2, 1);
        assertEquals(expectedOutput, actualOutput2);

    }

    @Test
    public void getProductMultipleRequestFirstProductEmptyTest() throws Exception {

        Product product = new Product(1, 0, "Product");
        Product product2 = new Product(2, 5, "Product");

        VendingMachineOutput expectedOutput = new VendingMachineOutput(1,
                product2, new Amount(0),new Amount(5),OperationStatus.PRODUCT_DISPATCHED);

        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);
        when(productService.getProduct(1)).thenReturn(product);

        assertThrows(Exception.class, () ->  vendingMachineController.product(1, 1));


        when(productService.getProduct(2)).thenReturn(product2);
        VendingMachineOutput actualOutput2 = vendingMachineController.product(2, 1);
        assertEquals(expectedOutput, actualOutput2);

    }

}
