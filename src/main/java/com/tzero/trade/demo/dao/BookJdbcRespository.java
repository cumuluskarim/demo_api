package com.tzero.trade.demo.dao;

import com.tzero.trade.demo.entity.BookEntity;
import com.tzero.trade.demo.model.BookType;
import com.tzero.trade.demo.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;


@Repository
public class BookJdbcRespository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger logger = Logger.getLogger("BookJdbcRespository");

    public void save(BookEntity bookEntity) {
        String query = "INSERT INTO book(quantity_placed, quantity_filled, price, type, order_date, status)" +
                " values(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(query, new Object[] {bookEntity.getQuantityPlaced(), bookEntity.getQuantityFilled(),
                bookEntity.getPrice(), bookEntity.getType().name(), bookEntity.getDate(), bookEntity.getStatus().name()});
    }

    public void save(List<BookEntity> bookEntities) {
        String query = "UPDATE book SET quantity_filled = ?, status = ? WHERE id = ?";
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                BookEntity bookEntity = bookEntities.get(i);
                ps.setLong(1, bookEntity.getQuantityFilled());
                ps.setString(2, bookEntity.getStatus().name());
                ps.setInt(3, bookEntity.getId());
            }

            @Override
            public int getBatchSize() {
                return bookEntities.size();
            }
        });
    }

    public List<BookEntity> getPlacedOrders() {
        String query = "SELECT * FROM book WHERE status = ?";
        List<BookEntity> books = jdbcTemplate.query(query, new Object[] {Status.PLACED.name()},
                new BeanPropertyRowMapper<>(BookEntity.class));
        return books;
    }

    public List<BookEntity> getPlacedOrdersByPrice(float price, BookType type) {
        String query;
        if (BookType.BUY == type){
           query = "SELECT * FROM book WHERE status = ? AND price >= ? AND type = ? ORDER BY price desc";
           logger.info("SELECT * FROM book WHERE status = '"+Status.PLACED.name() +"' AND price <= "+price +" AND type ='"+ type.name() +"' ORDER BY price desc");
        } else {
           query = "SELECT * FROM book WHERE status = ? AND price <= ? AND type = ? ORDER BY price desc";
            logger.info("SELECT * FROM book WHERE status = '"+Status.PLACED.name() +"' AND price >= "+price +" AND type ='"+ type.name() +"' ORDER BY price desc");
        }

        List<BookEntity> books = jdbcTemplate.query(query, new Object[] {Status.PLACED.name(), price, type.name()},
                new BeanPropertyRowMapper<>(BookEntity.class));
        return books;
    }
}
