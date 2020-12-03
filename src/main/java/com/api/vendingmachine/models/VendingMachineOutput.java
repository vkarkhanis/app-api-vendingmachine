package com.api.vendingmachine.models;

import util.OperationStatus;

import java.util.Objects;

public class VendingMachineOutput {

    private int requestId;
    private Product productToDispatch;
    private OperationStatus operationStatus;
    private Amount change;
    private Amount currentBalance;

    @Override
    public int hashCode() {
        return Objects.hash(requestId, productToDispatch, operationStatus, change, currentBalance);
    }

    public VendingMachineOutput(int requestId,
                                Product productToDispatch,
                                Amount currentBalance,
                                Amount change,
                                OperationStatus operationStatus) {

        this.requestId = requestId;
        this.productToDispatch = productToDispatch;
        this.operationStatus = operationStatus;
        this.currentBalance = currentBalance;
        this.change = change;
    }

    public int getRequestId() {
        return requestId;
    }

    public Product getProductToDispatch() {
        return productToDispatch;
    }

    public OperationStatus getOperationStatus() { return operationStatus; }

    public Amount getChange() { return change; }

    public Amount getCurrentBalance() { return currentBalance; }

    @Override
    public String toString() {
        return "VendingMachineOutput{" +
                "requestId=" + requestId +
                ", productToDispatch=" + productToDispatch +
                ", operationStatus=" + operationStatus +
                ", change=" + change +
                ", currentBalance=" + currentBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendingMachineOutput that = (VendingMachineOutput) o;

        boolean isProductEqual = false;
        if (productToDispatch == null) {
            isProductEqual = (that.productToDispatch == null);
        } else {
            isProductEqual = productToDispatch.equals(that.productToDispatch);
        }

        return requestId == that.requestId &&
                isProductEqual  &&
                operationStatus == that.operationStatus &&
                change.equals(that.change) &&
                currentBalance.equals(that.currentBalance);
    }
}
