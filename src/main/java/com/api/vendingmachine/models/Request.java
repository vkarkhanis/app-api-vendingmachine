package com.api.vendingmachine.models;

import util.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="request")
public class Request {

    public Request() {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double balance;
    private Status status;

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                Double.compare(request.balance, balance) == 0 &&
                status == request.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, status);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", balance=" + balance +
                ", status=" + status +
                '}';
    }
}
