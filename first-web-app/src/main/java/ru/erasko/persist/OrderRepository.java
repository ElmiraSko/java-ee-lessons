package ru.erasko.persist;

import ru.erasko.persist.entity.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private final Connection conn;

    public OrderRepository (Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExist(conn);
    }

    private void createTableIfNotExist(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()){
            statement.execute(
                    "create table if not exists orders (\n" +
                            "id int auto_increment primary key, \n" +
                            "customer_id int,\n" +
                            "qty int,\n" +
                            "total_price DECIMAL(10,2),\n" +
                            "date_time DATE);"
            );
        }
    }
    public void insert(Order order) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "insert into orders(customer_id, qty, total_price, date_time) value (?, ?, ?, ?);")){
            statement.setLong(1, order.getCustomerId());
            statement.setInt(2, order.getQty());
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.setDate(4,  order.getDate());
            statement.execute();
        }
    }

    public void update(Order order) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "update orders set customer_id = ?, qty = ?, total_price = ? where id = ?;")){
            statement.setLong(1, order.getCustomerId());
            statement.setInt(2, order.getQty());
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.setLong(4, order.getId());
            statement.execute();
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "delete from orders where id = ?;")){
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public Optional<Order> findById(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "select id, customer_id, qty, total_price, date_time from orders where id = ?;")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Long orderId = rs.getLong("id");
                Long customer_id = rs.getLong("customer_id");
                int orderQty = rs.getInt("qty");
                BigDecimal totalPrice = rs.getBigDecimal("total_price");
                Date date = rs.getDate("date_time");

                return Optional.of(new Order(orderId, customer_id,
                        orderQty, totalPrice, date));

            }
        }
        return Optional.empty();
    }

    public List<Order> findAll() throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(
                "select id, customer_id, qty, total_price, date_time from orders;")){
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                orders.add(new Order(rs.getLong(1), rs.getLong(2),
                        rs.getInt(3), rs.getBigDecimal(4), rs.getDate(5)));

            }
        }
        return orders;
    }

}
