package com.api.vendingmachine.controllers;

import com.api.vendingmachine.exceptions.GenericException;
import com.api.vendingmachine.exceptions.IncorrectPayloadException;
import com.api.vendingmachine.exceptions.InsufficientBalanceException;
import com.api.vendingmachine.exceptions.OrderCreationException;
import com.api.vendingmachine.exceptions.OrderNotFoundException;
import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Order;
import com.api.vendingmachine.models.Product;
import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
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

import util.Status;

@RestController
@RequestMapping("/vendingmachine/v2")
@CrossOrigin(origins="http://localhost:3000")
public class OrderController {

    @Autowired
    private MoneyService moneyService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private ProductService productService;
 
    @PostMapping("/order")
    public Order createOrder(@RequestBody Amount amount) 
        throws OrderCreationException, IncorrectPayloadException {
        try {

            if (amount == null || amount.getAmount() <= 0) {
                throw new IncorrectPayloadException("No amount entered by the user");
            }
            Request req = moneyService.addMoney(amount);
            if (req != null) {

                Order order = new Order(req.getId(), req.getBalance(), req.getStatus());
                return order;
            } 
            throw new GenericException("Internal Error. Please contact API administrator");
        } catch(IncorrectPayloadException e) {
            throw e;
        } catch(Exception e) {
            throw new OrderCreationException("Exception while creating order: "+ e.getMessage() +  
           " Please contact API Administrator");
        }
        
    }

    @PutMapping("/order/{orderId}")
    public Order updateOrder(@RequestBody Amount amount, @PathVariable int orderId) 
        throws OrderNotFoundException, GenericException, IncorrectPayloadException {

        if (amount == null || amount.getAmount() <= 0) {
            throw new IncorrectPayloadException("No amount entered by the user");
        }
        
        Request req = null;

        try {
            req = moneyService.updateMoney(amount, orderId);
        } catch(Exception e) {
            throw new GenericException("Internal error, please contact API administrator", e);
        }
        
        if (req != null) {

            Order order = new Order(req.getId(), req.getBalance(), req.getStatus());
            return order;
        } 
        throw new OrderNotFoundException("Order with order id: " + orderId + " not found");
    }

    @GetMapping("/order/{orderId}/refund")
    public double refund(@PathVariable int orderId) 
        throws OrderNotFoundException, IncorrectPayloadException {

        if (orderId < 0) {
            throw new IncorrectPayloadException("Invalid orderId: " + orderId);
        }

        Request req = requestService.getPendingRequestById(orderId);
        if (req == null) {
            throw new OrderNotFoundException("Order with Order Id: " + orderId + " not found");
        }
        
        double balance = req.getBalance();

        moneyService.makeRefund(req);
        return balance;
    }

    @GetMapping("/order/{orderId}/product/{productId}")
    public Order getProduct(@PathVariable int orderId, @PathVariable int productId) 
        throws OrderNotFoundException, IncorrectPayloadException, GenericException, 
        InsufficientBalanceException{
        

        if (orderId < 0) {
            throw new IncorrectPayloadException("Invalid orderId: " + orderId);
        }
        if (productId <= 0) {
            throw new IncorrectPayloadException("Invalid product id: " + productId);
        }

        Request req = requestService.getRequestByIdAndStatus(orderId, Status.MONEY_ADDED);

        if (req == null) {
            throw new OrderNotFoundException("Order with Order Id: " + orderId + " not found");
        }

        Product product;
        try {
            product = productService.getProduct(productId);
        } catch (Exception e) {
            throw new GenericException("Internal error while fetching product details: " + e.getLocalizedMessage(), e);
        }

        if(product.getPrice() <= 0) {
            throw new GenericException("Product price not found");
        }

        if (product.getPrice() > req.getBalance()) {
            throw new InsufficientBalanceException("Insufficient balance");     
        }

        try {
            req.setStatus(Status.PROCESSING_ORDER);
            requestService.updateRequest(req);

            double change = req.getBalance() - product.getPrice();

            req.setBalance(0);
            req.setStatus(Status.REQUEST_COMPLETED);
            requestService.updateRequest(req);

            Order order = new Order(orderId, change, Status.REQUEST_COMPLETED);
            order.setProduct(product);
            return order;

        } catch(Exception e) {
           throw new GenericException("Processing error. Please contact customer service", e);
        }
    }
}
