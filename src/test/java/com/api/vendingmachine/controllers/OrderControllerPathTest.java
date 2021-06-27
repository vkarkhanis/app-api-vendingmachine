package com.api.vendingmachine.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
   
import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class OrderControllerPathTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MoneyService moneyService;
    @MockBean
    private RequestService requestService;
    @MockBean
    private ProductService productService;
    
    @Test
    public void createOrder() throws Exception {
        when(moneyService.addMoney(new Amount(20))).thenReturn(new Request());
        mockMvc.perform(post("/vendingmachine/v2/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderMissingRequestBody() throws Exception {
        when(moneyService.addMoney(new Amount(20))).thenReturn(new Request());
        mockMvc.perform(post("/vendingmachine/v2/order"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void createOrderIncorrectRequestBody() throws Exception {
        when(moneyService.addMoney(new Amount(20))).thenReturn(new Request());
        mockMvc.perform(post("/vendingmachine/v2/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"somekey\": }"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void createOrderMissingAmount() throws Exception {
        when(moneyService.addMoney(new Amount(20))).thenReturn(new Request());
        mockMvc.perform(post("/vendingmachine/v2/order")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrder() throws Exception {
        when(moneyService.updateMoney(new Amount(20), 1)).thenReturn(new Request());
        mockMvc.perform(put("/vendingmachine/v2/order/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"amount\": 20}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderMissingRequestBody() throws Exception {
        when(moneyService.updateMoney(new Amount(20), 1)).thenReturn(new Request());
        mockMvc.perform(put("/vendingmachine/v2/order/1"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderIncorrectRequestBody() throws Exception {
        when(moneyService.updateMoney(new Amount(20), 1)).thenReturn(new Request());
        mockMvc.perform(put("/vendingmachine/v2/order/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"somekey\": }"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void updateOrderMissingAmount() throws Exception {
        mockMvc.perform(put("/vendingmachine/v2/order/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void refund() throws Exception {
        when(requestService.getPendingRequestById(1)).thenReturn(new Request());
        
        mockMvc.perform(get("/vendingmachine/v2/order/1/refund")).andExpect(status().isOk());
    }

    @Test
    public void refundIncorrectOrderId() throws Exception {
        mockMvc.perform(get("/vendingmachine/v2/order/-1/refund"))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void refundMissingOrderId() throws Exception {
        mockMvc.perform(get("/vendingmachine/v2/order//refund"))
        .andExpect(status().is4xxClientError());
    }

    @Test
    public void getProduct() throws Exception {
        mockMvc.perform(get("/vendingmachine/v2/order/1/product/1")).andExpect(status().isOk());
    }
    
}
