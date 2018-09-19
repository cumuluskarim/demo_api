package com.tzero.trade.demo.util;

import com.tzero.trade.demo.model.Order;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class SortByPriceAscending implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.getPrice() > o2.getPrice()) {
            return 1;
        }else if (o1.getPrice() == o2.getPrice()) {
            return 0;
        }
        return -1;
    }
}
