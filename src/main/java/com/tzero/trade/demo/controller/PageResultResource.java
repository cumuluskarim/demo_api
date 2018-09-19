package com.tzero.trade.demo.controller;

import com.tzero.trade.demo.exception.DataErrorException;
import com.tzero.trade.demo.model.BookType;
import com.tzero.trade.demo.model.Order;
import com.tzero.trade.demo.model.OrderJson;
import com.tzero.trade.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageResultResource {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/book", method = RequestMethod.GET, produces = "application/json")
    OrderJson bookOrder() {
        return orderService.bookOrders();
    }

    @RequestMapping(value = "/sell", method = RequestMethod.POST, consumes = "application/json")
    void sellOrder(@RequestBody Order order) throws DataErrorException {
        if (isValid(order)) {
            orderService.placeOrder(order, BookType.SELL);
        }
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST, consumes = "application/json")
    void buyOrder(@RequestBody Order order) throws DataErrorException {
        if(isValid(order)) {
            orderService.placeOrder(order, BookType.BUY);
        }
    }

    private boolean isValid(Order order) {
        if (order.getQuantity() < 1 ){
            throw new DataErrorException("Quantity Cannot be zero or negative");
        } else if(order.getPrice() < 0) {
            throw new DataErrorException("Price cannot be negative");
        }
        return true;
    }
}
