package com.api.vendingmachine.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Money")
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private DenominationType denominationType;
    private float value;
    private int number;

    public Money(){}

    public Money(DenominationType denominationType, int value, int number) {
        this.denominationType = denominationType;
        this.value = value;
        this.number = number > 0 ? number : 1;
    }

    public DenominationType getDenominationType() {
        return denominationType;
    }

    public float getValue() {
        return value;
    }

    public int getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return id == money.id &&
                Float.compare(money.value, value) == 0 &&
                number == money.number &&
                denominationType == money.denominationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, denominationType, value, number);
    }

    @Override
    public String toString() {
        return "Money{" +
                "id=" + id +
                ", denominationType=" + denominationType +
                ", value=" + value +
                ", number=" + number +
                '}';
    }
}
