package com.api.vendingmachine.controllers;

import com.api.vendingmachine.exceptions.IncorrectPayloadException;
import com.api.vendingmachine.exceptions.OrderCreationException;
import com.api.vendingmachine.exceptions.OrderNotFound;
import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Order;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendingmachine/v2")
@CrossOrigin(origins="http://localhost:3000")
public class OrderController {

    @Autowired
    private MoneyService moneyService;
    @Autowired
    private RequestService requestService;
 
    @PostMapping("/order")
    public int createOrder(@RequestBody Amount amount) throws OrderCreationException, IncorrectPayloadException {

        try {

            if (amount == null || amount.getAmount() <= 0) {
                throw new IncorrectPayloadException("No amount entered by the user");
            }
            Request req = moneyService.addMoney(amount, 0);
            if (req != null) {
                return req.getId();
            } 
            throw new Exception();
        } catch(IncorrectPayloadException e) {
            throw new IncorrectPayloadException(e.getLocalizedMessage());
        } catch(Exception e) {
            throw new OrderCreationException("Exception while creating order. "+ 
           " Please contact API Administrator");
        }
        
    }

    @PutMapping("/order/{orderId}")
    public int updateOrder(@RequestBody Amount amount, @PathVariable int orderId) 
    throws OrderNotFound, OrderCreationException, IncorrectPayloadException {

        if (amount == null || amount.getAmount() <= 0) {
            throw new IncorrectPayloadException("No amount entered by the user");
        }
        return moneyService.addMoney(amount, orderId).getId();
    }

    @GetMapping("/order/{orderId}/refund")
    public double refund(@PathVariable int orderId) throws OrderNotFound, IncorrectPayloadException {
        Request req = requestService.getPendingRequestById(orderId);

        if (orderId < 0) {
            throw new IncorrectPayloadException("Invalid orderId: " + orderId);
        }
        if (req == null) {
            throw new OrderNotFound("Order with Order Id: " + orderId + " not found");
        }

        moneyService.makeRefund(req);
        return req.getBalance();
    }

    @GetMapping("/order/{orderId}/product/{productId}")
    public Order getProduct(@PathVariable int orderId, @PathVariable int productId) {
        return null;
    }
}
