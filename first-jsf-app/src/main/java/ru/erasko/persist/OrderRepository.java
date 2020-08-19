package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Order;
import ru.erasko.persist.entity.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class OrderRepository {

    private final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public OrderRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("jdbcConnection");
        createTableIfNotExist(conn);

        if (this.findAll().isEmpty()) {
            logger.info("No order in DB. Initialized");
            this.insert(new Order(-1L, 3L, 1, new BigDecimal(13400)));
        }
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
