package com.api.vendingmachine.services;

import com.api.vendingmachine.exceptions.OrderCreationException;
import com.api.vendingmachine.exceptions.OrderNotFoundException;
import com.api.vendingmachine.models.Amount;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.vendingmachine.models.Request;
import org.springframework.stereotype.Component;
import util.Status;

@Component
public class MoneyService {

    @Autowired
    private RequestService requestService;

    public MoneyService() {}

    public Request addMoney(Amount amount) throws OrderCreationException {

        Request reqCreated = null;

        Request req = new Request();
        req.setBalance(amount.getAmount());
        req.setStatus(Status.MONEY_ADDED);

        try {
            reqCreated = requestService.updateRequest(req);
        } catch(Exception e) {
            throw new OrderCreationException("Exception while creating new Order", e);
        }
         

        return reqCreated;
    }

    public Request updateMoney(Amount amount, int requestId) throws OrderNotFoundException {

        Request reqCreated = null;
        Request existingReq = requestService.getRequestByIdAndStatus(requestId, Status.MONEY_ADDED);

        if(existingReq != null) {
            existingReq.setBalance(existingReq.getBalance() + amount.getAmount());
            reqCreated = requestService.updateRequest(existingReq);

        } else {
            throw new OrderNotFoundException("Could not find Order with order id: " + requestId);
        }
        return reqCreated;
    }

    public Request makeRefund(Request req) {

        req.setStatus(Status.MONEY_REFUNDED);
        req.setBalance(0);

        return requestService.updateRequest(req);
    }

}
