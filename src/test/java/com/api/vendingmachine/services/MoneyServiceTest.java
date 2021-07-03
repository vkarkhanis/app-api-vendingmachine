package com.api.vendingmachine.services;

import com.api.vendingmachine.models.Amount;
import com.api.vendingmachine.models.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoneyServiceTest {

    @Mock
    private RequestService requestService;

    @InjectMocks
    private MoneyService moneyService;

    private Request request;

    @BeforeEach
    public void setUp() {
        request = new Request();
        request.setBalance(20);
        request.setStatus(Status.MONEY_ADDED);
    }

    @Test
    public void addMoney() throws Exception {

        Request addReq = new Request();
        addReq.setStatus(Status.MONEY_ADDED);
        addReq.setBalance(20);

        when(requestService.updateRequest(addReq)).thenReturn(request);

        when(requestService.getRequestByIdAndStatus(1, Status.MONEY_ADDED)).thenReturn(addReq);

        Request updateReq = new Request();
        updateReq.setStatus(Status.MONEY_ADDED);
        updateReq.setBalance(50);
        when(requestService.updateRequest(updateReq)).thenReturn(updateReq);

        Request req = moneyService.addMoney(new Amount(20));
        assertEquals(20, req.getBalance());

        req = moneyService.addMoney(new Amount(30));
        assertEquals(50, req.getBalance());

    }

    @Test
    public void addMoneyInvalidRequest() throws Exception {

        when(requestService.getRequestByIdAndStatus(1, Status.REQUEST_COMPLETED)).thenReturn(null);
        assertThrows(Exception.class, () -> moneyService.addMoney(new Amount(20)));
    }

    @Test
    public void refundRequestTest() {

        Request terminateRequest = new Request();
        terminateRequest.setStatus(Status.MONEY_REFUNDED);
        terminateRequest.setBalance(0);

        when(requestService.updateRequest(request)).thenReturn(terminateRequest);
        Request req = moneyService.makeRefund(request);

        assertEquals(terminateRequest, req);
    }

}
