package com.api.vendingmachine.models;

import java.util.Objects;

public class Amount {

    private double amount;

    public Amount() {}

    public Amount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public Amount addAmount(Amount amount) {

        return new Amount(0);
    }

    public Amount deductAmount(Amount amount) {

        return new Amount(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Double.compare(amount1.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                '}';
    }
}
