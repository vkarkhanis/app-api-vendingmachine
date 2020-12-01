package com.api.vendingmachine.controllers;

import com.api.vendingmachine.models.*;
import com.api.vendingmachine.services.MoneyService;
import com.api.vendingmachine.services.ProductService;
import com.api.vendingmachine.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.OperationStatus;
import util.Status;

@RestController
@RequestMapping("/vendingmachine")
public class VendingMachineController {

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ProductService productService;

    @PostMapping("/amount")
    public VendingMachineOutput amount(@RequestBody Amount amount) throws Exception {
        try {
            Request req = moneyService.addMoney(amount, 0);
            return new VendingMachineOutput(req.getId(), null, new DisplayMessages("Current Balance: " + req.getBalance(), "Awaiting product selection"), OperationStatus.SUCCESS);

        } catch (Exception e) {
            return new VendingMachineOutput(0, null, new DisplayMessages("Current Balance: " + (0), "There was some problem in processing your order. PLease collect your refund"), amount, OperationStatus.FAILURE);
        }

    }

    @PutMapping("/amount")
    public VendingMachineOutput amount(@RequestBody Amount amount, @RequestParam int requestId) throws Exception {

        try {
            Request req = moneyService.addMoney(amount, requestId);
            return new VendingMachineOutput(req.getId(), null, new DisplayMessages("Current Balance: " + req.getBalance(), "Awaiting product selection"), OperationStatus.SUCCESS);

        } catch (Exception e) {
            System.err.println(e);
            return new VendingMachineOutput(requestId, null, new DisplayMessages("Error showing the current balance. Please contact helpdesk", "There was some problem adding amount. Please collect back the newly added amount"), amount, OperationStatus.FAILURE);
        }

    }

    @GetMapping("/amount")
    public VendingMachineOutput refund(@RequestParam int requestId) throws Exception {

        try {
            Request req = requestService.terminateRequest(requestId);
            return new VendingMachineOutput(req.getId(), null, new DisplayMessages("Current Balance: " + (0), "Refund done"), new Amount(req.getBalance()), OperationStatus.SUCCESS);
        } catch(Exception e) {
            System.err.println(e);
            return new VendingMachineOutput(requestId, null, new DisplayMessages("Current Balance: " + (0), "Error when processing refund. Please contact support"), OperationStatus.FAILURE);
        }

    }

    @GetMapping("/product/{productId}")
    public VendingMachineOutput product(@PathVariable int productId, @RequestParam int requestId) throws Exception {

        try {
            Request req = requestService.getRequest(requestId);
            req.setStatus(Status.PROCESSING_ORDER);
            requestService.updateRequest(req);

            Product product = productService.getProduct(productId);
            Amount change = moneyService.calculateChange(new Amount(product.getPrice()), requestId);

            req.setBalance(0);
            req.setStatus(Status.REQUEST_COMPLETED);
            requestService.updateRequest(req);

            return new VendingMachineOutput(requestId, product, new DisplayMessages("Changed To Be Returned: " + (change.getAmount()), "Order Completed"), change, OperationStatus.SUCCESS);

        } catch(Exception e) {
            System.err.println(e);

            return this.refund(requestId);
        }
    }

}
