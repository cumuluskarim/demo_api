package com.tzero.trade.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView
public class Order {

    @JsonProperty("qty")
    private long quantity;
    @JsonProperty("prc")
    private float price;

    public Order(long quantity, float price) {
        this.quantity = quantity;
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
