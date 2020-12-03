package com.api.vendingmachine.services;

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

    public Request addMoney(Amount amount, int requestId) throws Exception {

        Request reqCreated = null;

        if(requestId <= 0) {
            Request req = new Request();
            req.setBalance(amount.getAmount());
            req.setStatus(Status.MONEY_ADDED);

            reqCreated = requestService.updateRequest(req);

        } else {
            Request existingReq = requestService.getRequestByIdAndStatus(requestId, Status.MONEY_ADDED);

            if(existingReq != null) {
                existingReq.setBalance(existingReq.getBalance() + amount.getAmount());
                reqCreated = requestService.updateRequest(existingReq);

            } else {
                throw new Exception("Illegal request made");
            }
        }

        return reqCreated;
    }

    public Request makeRefund(Request req) {

        req.setStatus(Status.MONEY_REFUNDED);
        req.setBalance(0);

        return requestService.updateRequest(req);
    }

}