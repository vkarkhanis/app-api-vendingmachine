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

            if(req == null) {
                throw new Exception("Error while adding amount");
            }
            return new VendingMachineOutput(req.getId(), null,
                                            new Amount(req.getBalance()), new Amount(0),
                                            OperationStatus.MONEY_ADDED);

        } catch (Exception e) {
            throw new Exception("Error while adding amount", e);
        }

    }

    @PutMapping("/amount")
    public VendingMachineOutput amount(@RequestBody Amount amount, @RequestParam int requestId) throws Exception {

        try {
            Request req = moneyService.addMoney(amount, requestId);
            return new VendingMachineOutput(req.getId(), null,
                    new Amount(req.getBalance()),  new Amount(0), OperationStatus.MONEY_UPDATED);

        } catch (Exception e) {
            throw new Exception("Exception while updating the amount for request id: " + requestId, e);
        }

    }

    @GetMapping("/amount")
    public VendingMachineOutput refund(@RequestParam int requestId) throws Exception {

        try {
            Request req = requestService.getPendingRequestById(requestId);
            if (req == null) {
                throw new Exception("No request with the given request Id: " + requestId + "found");
            }

            double currentBalance = req.getBalance();

            moneyService.makeRefund(req);

            return new VendingMachineOutput(req.getId(), null,
                    new Amount(0), new Amount(currentBalance), OperationStatus.REFUND_PROCESSED);

        } catch(Exception e) {
            throw new Exception("Error when processing refund. Please contact customer service", e);
        }

    }

    @GetMapping("/product/{productId}")
    public VendingMachineOutput product(@PathVariable int productId, @RequestParam int requestId) throws Exception {

        try {
            Request req = requestService.getRequestByIdAndStatus(requestId, Status.MONEY_ADDED);

            if (req == null) {
                throw new Exception("No request with the given request Id: " + requestId + "found");
            }

            req.setStatus(Status.PROCESSING_ORDER);
            requestService.updateRequest(req);

            Product product = productService.getProduct(productId);

            if(product.getPrice() <= 0) {
                throw new Exception("Product price not found");
            }

            if (product.getPrice() > req.getBalance()) {

                return new VendingMachineOutput(requestId, null,
                        new Amount(req.getBalance()), new Amount(0),OperationStatus.INSUFFICIENT_BALANCE);

            }

            double change = req.getBalance() - product.getPrice();

            req.setBalance(0);
            req.setStatus(Status.REQUEST_COMPLETED);
            requestService.updateRequest(req);

            return new VendingMachineOutput(requestId,
                    product, new Amount(0),new Amount(change),OperationStatus.PRODUCT_DISPATCHED);

        } catch(Exception e) {
           throw new Exception("Processing error. Please contact customer service", e);
        }
    }

}
