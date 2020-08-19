package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductRepository {
    private final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public ProductRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("jdbcConnection");
        createTableIfNotExist(conn);

        if (this.findAll().isEmpty()) {
            logger.info("No product in DB. Initialized");
            this.insert(new Product(-1L, "Samsung Galaxy Tab S7", "Samsung Galaxy Tab S7 description", new BigDecimal(13400)));
        }
    }

    private void createTableIfNotExist(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()){
            statement.execute(
                "create table if not exists products (\n" +
                "id int auto_increment primary key, \n" +
                "name VARCHAR(25),\n" +
                "description VARCHAR(255),\n" +
                "price DECIMAL(10,2));"
            );
        }
    }
    public void insert(Product product) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "insert into products(name, description, price) value (?, ?, ?);")){
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "update products set name = ?, description = ?, price = ? where id = ?;")){
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setLong(4, product.getId());
            statement.execute();
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "delete from products where id = ?;")){
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public Optional<Product> findById(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "select id, name, description, price from products where id = ?;")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Long prodId = rs.getLong("id");
                String prodName = rs.getString("name");
                String prodDescription = rs.getString("description");
                BigDecimal prodPrice = rs.getBigDecimal("price");

                return Optional.of(new Product(prodId, prodName,
                        prodDescription, prodPrice));

            }
        }
        return Optional.empty();
    }

    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<Product>();

        try (PreparedStatement statement = conn.prepareStatement(
                "select id, name, description, price from products;")){
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                products.add(new Product(rs.getLong(1), rs.getString(2),
                        rs.getString(3), rs.getBigDecimal(4)));

            }
        }
        return products;
    }

}
