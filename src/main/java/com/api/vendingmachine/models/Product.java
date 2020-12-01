package com.api.vendingmachine.models;

import java.util.Objects;

public class Product {

    private int id;
    private double price;
    private String name;

    public Product(int id, double price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.id == product.id && Double.compare(product.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name);
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
