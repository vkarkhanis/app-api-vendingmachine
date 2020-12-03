package com.api.vendingmachine.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="product_inventory")
public class ProductInventory {

    @Id
    private int productId;
    private int count;

    public ProductInventory(){}

    public ProductInventory(int productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public int getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInventory that = (ProductInventory) o;
        return productId == that.productId &&
                count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "productId=" + productId +
                ", count=" + count +
                '}';
    }
}
