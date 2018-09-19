package com.tzero.trade.demo.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderJson {

    private List<Order> buy;
    private List<Order> sell;

    public OrderJson() {

    }

    public OrderJson(List<Order> buy, List<Order> sell) {
        this.buy = buy;
        this.sell = sell;
    }

    public List<Order> getBuy() {
        return buy;
    }

    public void setBuy(List<Order> buy) {
        this.buy = buy;
    }

    public List<Order> getSell() {
        return sell;
    }

    public void setSell(List<Order> sell) {
        this.sell = sell;
    }
}
