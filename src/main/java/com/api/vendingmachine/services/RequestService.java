package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.store.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.Status;

import java.util.Optional;

@Component
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public RequestService() {}

    public Request updateRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request getRequest(int requestId) throws Exception {

        Optional<Request> existingRequest = requestRepository.findById(requestId);
        if(existingRequest.isPresent()) {
            return existingRequest.get();
        } else {
            throw new Exception("Invalid request id");
        }
    }

    public Request terminateRequest(int requestId) throws Exception {
        Request req = getPendingRequestById(requestId);

        if (req == null) {
            throw new Exception("Invalid request id");
        }

        req.setStatus(Status.MONEY_REFUNDED);
        req.setBalance(0);

        return requestRepository.save(req);
    }

    public Request getPendingRequestById(int requestId) {
        return requestRepository.fetchPendingRequestById(requestId);
    }

    public Request getRequestByIdAndStatus(int requestId, Status status) {
        return requestRepository.fetchRequestByIdAndStatus(requestId, status);
    }
}
