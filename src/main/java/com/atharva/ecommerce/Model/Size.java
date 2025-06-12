package com.atharva.ecommerce.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Size {
    private String name;
    private int quantity;

    public Size(){}
    public Size(String name, int quantity) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
