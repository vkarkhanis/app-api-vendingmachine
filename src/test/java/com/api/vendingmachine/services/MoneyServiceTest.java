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

        Request req = moneyService.addMoney(new Amount(20), 0);
        assertEquals(20, req.getBalance());

        req = moneyService.addMoney(new Amount(30), 1);
        assertEquals(50, req.getBalance());

    }

    @Test
    public void addMoneyInvalidRequest() throws Exception {

        when(requestService.getRequestByIdAndStatus(1, Status.REQUEST_COMPLETED)).thenReturn(null);
        assertThrows(Exception.class, () -> moneyService.addMoney(new Amount(20), 1));
    }

    @Test
    public void calculateChange() throws Exception {

        when(requestService.getRequest(1)).thenReturn(request);

        Amount returnedAmount = moneyService.calculateChange(new Amount(5), 1);
        assertEquals(15, returnedAmount.getAmount());

    }

    @Test
    public void calculateChangeNullProductCost() throws Exception {
        when(requestService.getRequest(1)).thenReturn(request);
        assertThrows(Exception.class, () -> moneyService.calculateChange(null, 1));

    }

    @Test
    public void calculateChangeNegativeProductCost() throws Exception {
        when(requestService.getRequest(1)).thenReturn(request);
        assertThrows(Exception.class, () -> moneyService.calculateChange(new Amount(-1), 1));

    }

    @Test
    public void calculateChangeInsufficientBalance() throws Exception {
        when(requestService.getRequest(1)).thenReturn(request);
        assertThrows(Exception.class, () -> moneyService.calculateChange(new Amount(25), 1));

    }

    @Test
    public void calculateChangeInvalidRequest() throws Exception {
        when(requestService.getRequest(1)).thenReturn(null);
        assertThrows(Exception.class, () -> moneyService.calculateChange(new Amount(10), 1));

    }


}
