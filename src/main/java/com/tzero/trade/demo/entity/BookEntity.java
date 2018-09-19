package com.tzero.trade.demo.entity;

import com.tzero.trade.demo.model.BookType;
import com.tzero.trade.demo.model.Order;
import com.tzero.trade.demo.model.Status;

import java.util.Date;

public class BookEntity {

    private int id;
    private Status status;
    private BookType type;
    private long quantityPlaced;
    private long quantityFilled;
    private float price;
    private Date date;

    public BookEntity() {}

    public static BookEntity buildOrder(Order order, BookType bookType, Status status) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPrice(order.getPrice());
        bookEntity.setQuantityPlaced(order.getQuantity());
        bookEntity.setQuantityFilled(0);
        bookEntity.setStatus(status);
        bookEntity.setType(bookType);
        bookEntity.setDate(new Date());
        return bookEntity;
    }

    public static BookEntity buildOrder(float price, long quantityPlaced, long quantityFilled, BookType bookType, Status status) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setPrice(price);
        bookEntity.setQuantityPlaced(quantityPlaced);
        bookEntity.setQuantityFilled(quantityFilled);
        bookEntity.setStatus(status);
        bookEntity.setType(bookType);
        bookEntity.setDate(new Date());
        return bookEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public long getQuantityPlaced() {
        return quantityPlaced;
    }

    public void setQuantityPlaced(long quantityPlaced) {
        this.quantityPlaced = quantityPlaced;
    }

    public long getQuantityFilled() {
        return quantityFilled;
    }

    public void setQuantityFilled(long quantityFilled) {
        this.quantityFilled = quantityFilled;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id:").append(this.id).append(" Qtnplaced:").append(this.quantityPlaced).
                append(" QtnFilled:").append(this.quantityFilled).append(" price:").append(this.price).append(" status:").append(this.status);
        return  stringBuilder.toString();
    }
}
