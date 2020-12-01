package com.api.vendingmachine.controllers;

import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        mockMvc.perform(post("/vendingmachine/amount").contentType(MediaType.APPLICATION_JSON).content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addMoneyWithoutRequestIdTest() throws Exception {
        mockMvc.perform(put("/vendingmachine/amount")).andExpect(status().is4xxClientError());
    }

    @Test
    public void addBalanceTest() throws Exception {
        mockMvc.perform(put("/vendingmachine/amount?requestId=1").contentType(MediaType.APPLICATION_JSON).content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void processProductOrderTest() throws Exception {
        mockMvc.perform(get("/vendingmachine/product/1?requestId=1")).andExpect(status().isOk());
    }

    @Test
    public void refundTest() throws Exception {
        mockMvc.perform(get("/vendingmachine/amount?requestId=1")).andExpect(status().isOk());
    }
}
