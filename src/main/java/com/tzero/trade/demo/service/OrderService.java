package com.tzero.trade.demo.service;

import com.tzero.trade.demo.util.SortByPriceAscending;
import com.tzero.trade.demo.util.SortByPriceDescending;
import com.tzero.trade.demo.entity.BookEntity;
import com.tzero.trade.demo.model.BookType;
import com.tzero.trade.demo.model.Order;
import com.tzero.trade.demo.model.OrderJson;
import com.tzero.trade.demo.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tzero.trade.demo.dao.BookJdbcRespository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderService {

    @Autowired
    private BookJdbcRespository bookJdbcRespository;

    @Autowired
    private SortByPriceAscending sortByPriceAscending;

    @Autowired
    private SortByPriceDescending sortByPriceDescending;

    private Logger logger = Logger.getLogger("OrderService");

    public void placeOrder(Order order, BookType type) {
        List<BookEntity> bookEntities;
        final long currOrderQnty = order.getQuantity();
        long currFillQnty = 0;
        if (BookType.BUY == type) {
            bookEntities = bookJdbcRespository.getPlacedOrdersByPrice(order.getPrice(), BookType.SELL);
        }else {
            bookEntities = bookJdbcRespository.getPlacedOrdersByPrice(order.getPrice(), BookType.BUY);
        }

        for (BookEntity bookEntity : bookEntities) {
            logger.info("bookEntities size: " + bookEntity.toString());
            long bookPlacedQnty = bookEntity.getQuantityPlaced();
            long bookFilledQnty = bookEntity.getQuantityFilled();
            long bookRemQunty = bookPlacedQnty - bookFilledQnty;
            long currRemQnty = currOrderQnty - currFillQnty;
            if (currRemQnty <= currOrderQnty) {
                if (bookRemQunty >= currRemQnty) {
                    bookFilledQnty += currRemQnty;
                    currFillQnty += currRemQnty;
                } else {
                    currFillQnty += bookRemQunty;
                    bookFilledQnty += bookRemQunty;
                }
            }
            bookEntity.setQuantityFilled(bookFilledQnty);
            Status bookOrderStatus = (bookPlacedQnty == bookFilledQnty) ? Status.COMPLETED : Status.PLACED;
            bookEntity.setStatus(bookOrderStatus);
        }
        final Status currOrderStatus = (currOrderQnty == currFillQnty) ? Status.COMPLETED : Status.PLACED;
        BookEntity currBookEntity = BookEntity.buildOrder(order.getPrice(),currOrderQnty, currFillQnty, type, currOrderStatus);
        bookJdbcRespository.save(currBookEntity);
        bookJdbcRespository.save(bookEntities);
    }

    public OrderJson bookOrders() {
        List<BookEntity> books = bookJdbcRespository.getPlacedOrders();
        return createBooks(books);
    }

    private OrderJson createBooks(List<BookEntity> entities) {
        final List<Order> buyOrders = new ArrayList<>();
        final List<Order> sellOrders =  new ArrayList<>();
        entities.stream().forEach( bookEntity -> {
            long remQnty = bookEntity.getQuantityPlaced() - bookEntity.getQuantityFilled();
            if (BookType.BUY == bookEntity.getType()) {
                buyOrders.add(new Order(remQnty, bookEntity.getPrice()));
            } else {
                sellOrders.add(new Order(remQnty, bookEntity.getPrice()));
            }
        });
        Collections.sort(buyOrders, sortByPriceDescending);
        Collections.sort(sellOrders, sortByPriceAscending);
        final OrderJson orderJson = new OrderJson(buyOrders, sellOrders);
        return orderJson;
    }
}
