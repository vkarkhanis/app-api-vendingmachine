package com.api.vendingmachine.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.api.vendingmachine.exceptions.OrderCreationException;
import com.api.vendingmachine.exceptions.OrderNotFound;
import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.models.Order;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import util.Status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
public class OrderControllerTest {

    @MockBean
    private MoneyService moneyService;

    @MockBean
    private RequestService requestService;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private OrderController orderController;

    private Request request;

    @BeforeEach
    public void setUp() {

        request = new Request();
        request.setBalance(10);
        request.setStatus(Status.MONEY_ADDED);
    }

   
    @Test
    public void createOrder() throws Exception {
        when(moneyService.addMoney(new Amount(10d)))
        .thenReturn(request);

        Order order = orderController.createOrder(new Amount(10d));
        assertEquals(0, order.getId());
        assertEquals(10, order.getBalance());
      
    }

    @Test
    public void createOrderException() throws Exception {
        when(moneyService.addMoney(new Amount(10d))).thenThrow(OrderCreationException.class);
        assertThrows(OrderCreationException.class, () -> orderController.createOrder(new Amount(20d)));
    }

    @Test
    public void updateOrder() throws Exception {
        when(moneyService.updateMoney(new Amount(10d), 1))
        .thenReturn(request);

        Order order = orderController.updateOrder(new Amount(10d), 1);
        assertEquals(0, order.getId());
        assertEquals(10, order.getBalance());
      
    }

    @Test
    public void updateOrderException() throws Exception {
        when(moneyService.updateMoney(new Amount(20d), 1)).thenThrow(OrderNotFound.class);
        assertThrows(OrderNotFound.class, () -> orderController.updateOrder(new Amount(20d), 1));
    }
    
    @Test
    public void refund() throws Exception {
        when(requestService.getPendingRequestById(1)).thenReturn(request);
        
        double bal = orderController.refund(1);
        assertEquals(10, bal);
      
    }

    @Test
    public void refundMissingOrder() throws Exception {
        when(requestService.getPendingRequestById(1)).thenReturn(null);
        
        assertThrows(OrderNotFound.class, () -> orderController.refund(1));
    }
}
