package com.api.vendingmachine.models;

import util.OperationStatus;

public class VendingMachineOutput {

    private int requestId;
    private Product productToDispatch;
    private DisplayMessages messagesToShow;
    private OperationStatus operationStatus;
    private Amount change;

    public VendingMachineOutput(int requestId,
                                Product productToDispatch,
                                DisplayMessages messagesToShow,
                                OperationStatus operationStatus) {

        this.requestId = requestId;
        this.productToDispatch = productToDispatch;
        this.messagesToShow = messagesToShow;
        this.operationStatus = operationStatus;
    }

    public VendingMachineOutput(int requestId,
                                Product productToDispatch,
                                DisplayMessages messagesToShow,
                                Amount change,
                                OperationStatus operationStatus) {

        this(requestId, productToDispatch, messagesToShow, operationStatus);
        this.change = change;

    }

    public int getRequestId() {
        return requestId;
    }

    public Product getProductToDispatch() {
        return productToDispatch;
    }

    public DisplayMessages getMessagesToShow() {
        return messagesToShow;
    }

    public OperationStatus getOperationStatus() { return operationStatus; }
}
