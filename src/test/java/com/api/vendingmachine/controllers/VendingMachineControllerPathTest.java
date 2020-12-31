package com.api.vendingmachine.controllers;

import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Product;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import util.Status;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class VendingMachineControllerPathTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoneyService moneyService;
    @MockBean
    private RequestService requestService;
    @MockBean
    private ProductService productService;

    @Test
    public void addMoneyTest() throws Exception {
        when(moneyService.addMoney(new Amount(20), 0)).thenReturn(new Request());
        mockMvc.perform(post("/vendingmachine/amount").contentType(MediaType.APPLICATION_JSON).content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addMoneyWithoutRequestIdTest() throws Exception {
        mockMvc.perform(put("/vendingmachine/amount")).andExpect(status().is4xxClientError());
    }

    @Test
    public void addBalanceTest() throws Exception {
        when(moneyService.addMoney(new Amount(20), 1)).thenReturn(new Request());
        mockMvc.perform(put("/vendingmachine/amount?requestId=1").contentType(MediaType.APPLICATION_JSON).content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void processProductOrderTest() throws Exception {

        Request mockRequest = new Request();
        mockRequest.setBalance(20);
        mockRequest.setStatus(Status.MONEY_ADDED);

        Product mockProduct = new Product(1, 10, "Chips");

        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(mockRequest);
        when(productService.getProduct(1)).thenReturn(mockProduct);

        mockMvc.perform(get("/vendingmachine/product/1?requestId=1")).andExpect(status().isOk());
    }

    @Test
    public void refundTest() throws Exception {
        when(requestService.getPendingRequestById(1)).thenReturn(new Request());
        when(moneyService.makeRefund(new Request())).thenReturn(null);

        mockMvc.perform(get("/vendingmachine/amount?requestId=1")).andExpect(status().isOk());
    }

    @Test
    public void fetchAllProducts() throws Exception {
        mockMvc.perform(get("/vendingmachine/product")).andExpect(status().isOk());
    }
}
