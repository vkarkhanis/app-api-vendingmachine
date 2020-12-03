package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Request;
import com.api.vendingmachine.store.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.Status;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestService requestService;

    private Request request;
    private Request terminateRequest;

    @BeforeEach
    public void setUp() {
        request = new Request();
        request.setStatus(Status.MONEY_ADDED);
        request.setBalance(10);

        terminateRequest = new Request();
        terminateRequest.setStatus(Status.MONEY_REFUNDED);
        terminateRequest.setBalance(0);
    }

    @Test
    public void updateRequest() throws Exception {


        Request newReq = new Request();
        newReq.setBalance(20);
        newReq.setStatus(Status.PROCESSING_ORDER);
        when(requestRepository.save(newReq)).thenReturn(newReq);

        Request req = requestService.updateRequest(newReq);

        assertEquals(newReq, req);
    }

    @Test
    public void getRequest() throws Exception {
        when(requestRepository.findById(1)).thenReturn(Optional.of(request));
        Request req = requestService.getRequest(1);

        assertEquals(request, req);
    }

    @Test
    public void getRequestInvalidRequestId() {
        when(requestRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> requestService.getRequest(1));
    }

    @Test
    public void fetchPendingInvalidRequestByIdTest() {

        when(requestRepository.fetchPendingRequestById(0)).thenReturn(null);

        assertEquals(null, requestService.getPendingRequestById(0));
    }

    @Test
    public void fetchRequestByIdAndStatusTest() {

        when(requestRepository.fetchRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(request);

        Request req = requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED);
        assertEquals(request, req);
    }

    @Test
    public void fetchRequestByInvalidIdOrInvalidStatusTest() {

        when(requestRepository.fetchRequestByIdAndStatus(0, Status.REQUEST_COMPLETED)).thenReturn(null);

        assertEquals(null, requestService.getRequestByIdAndStatus(0, Status.REQUEST_COMPLETED));
    }
}
