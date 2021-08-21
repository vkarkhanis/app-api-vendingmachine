package com.api.vendingmachine.models;

import util.Status;

public class Order {

    private int id;
    private double balance;
    private Status status;
    private Product product;

    public Order(){}
    public Order(int id, double balance, Status status) {
        this.id = id;
        this.balance = balance;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Order [balance=" + balance + ", id=" + id + ", product=" + product + ", status=" + status + "]";
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + id;
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
            return false;
        if (id != other.id)
            return false;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.equals(other.product))
            return false;
        if (status != other.status)
            return false;
        return true;
    }
}
