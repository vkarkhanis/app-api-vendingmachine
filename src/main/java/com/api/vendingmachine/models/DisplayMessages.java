package com.api.vendingmachine.models;

public class DisplayMessages {

    private String balanceMessage;
    private String workflowMessage;

    public DisplayMessages(String balanceMessage, String workflowMessage) {
        this.balanceMessage = balanceMessage;
        this.workflowMessage = workflowMessage;
    }

    @Override
    public String toString() {
        return "DisplayMessages{" +
                "balanceMessage='" + balanceMessage + '\'' +
                ", workflowMessage='" + workflowMessage + '\'' +
                '}';
    }

    public String getBalanceMessage() {
        return balanceMessage;
    }

    public String getWorkflowMessage() {
        return workflowMessage;
    }
}
