package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.CartItem;
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
public class CartItemRepository {
    private final Logger logger = LoggerFactory.getLogger(CartItemRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public CartItemRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("jdbcConnection");
        createTableIfNotExist(conn);

        if (this.findAll().isEmpty()) {
            logger.info("No cartItem in DB. Initialized");
            this.insert(new CartItem(-1L, "Samsung Galaxy Tab S7", 2, new BigDecimal(13400)));
        }
    }
    public CartItemRepository(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExist(conn);
    }

    private void createTableIfNotExist(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()){
            statement.execute(
                    "create table if not exists cart (\n" +
                            "id int auto_increment primary key, \n" +
                            "prod_name VARCHAR(25),\n" +
                            "qty int,\n" +
                            "price DECIMAL(10,2));"
            );
        }
    }
    public void insert(CartItem cartItem) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "insert into cart(prod_name, qty, price) value (?, ?, ?);")){
            statement.setString(1, cartItem.getProdName());
            statement.setInt(2, cartItem.getQty());
            statement.setBigDecimal(3, cartItem.getPrice());
            statement.execute();
        }
    }

    public void update(CartItem cartItem) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "update cart set prod_name = ?, qty = ?, price = ? where id = ?;")){
            statement.setString(1, cartItem.getProdName());
            statement.setInt(2, cartItem.getQty());
            statement.setBigDecimal(3, cartItem.getPrice());
            statement.setLong(4, cartItem.getId());
            statement.execute();
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "delete from cart where id = ?;")){
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public Optional<CartItem> findById(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "select id, prod_name, qty, price from cart where id = ?;")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Long prodId = rs.getLong("id");
                String prodName = rs.getString("prod_name");
                int prodQty = rs.getInt("qty");
                BigDecimal price = rs.getBigDecimal("price");

                return Optional.of(new CartItem(prodId, prodName,
                        prodQty, price));

            }
        }
        return Optional.empty();
    }

    public List<CartItem> findAll() throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(
                "select id, prod_name, qty, price from cart;")){
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                cartItems.add(new CartItem(rs.getLong(1), rs.getString(2),
                        rs.getInt(3), rs.getBigDecimal(4)));

            }
        }
        return cartItems;
    }

}
