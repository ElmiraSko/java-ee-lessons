package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Catalog;
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
public class CatalogRepository {
    Logger logger = LoggerFactory.getLogger(CatalogRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public CatalogRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("jdbcConnection");
        createTableIfNotExist(conn);

        if (this.findAll().isEmpty()) {
            logger.info("No catalog in DB. Initialized");
            this.insert(new Catalog(-1L, "Phone"));
        }
    }

    private void createTableIfNotExist(Connection conn) throws SQLException {
        logger.info("createTableIfNotExist");
        try (Statement statement = conn.createStatement()){
            statement.execute(
                    "create table if not exists cataloges (\n" +
                            "id int auto_increment primary key, \n" +
                            "name VARCHAR(25));"
            );
        }
    }
    public void insert(Catalog catalog) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "insert into cataloges(name) value (?);")){
            statement.setString(1, catalog.getName());

            statement.execute();
        }
    }

    public void update(Catalog catalog) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "update cataloges set name = ? where id = ?;")){
            statement.setString(1, catalog.getName());
            statement.setLong(2, catalog.getId());
            statement.execute();
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "delete from cataloges where id = ?;")){
            statement.setLong(1, id);
            statement.execute();
        }
    }

    public Optional<Catalog> findById(Long id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(
                "select id, name from cataloges where id = ?;")){
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Long catalogId = rs.getLong("id");
                String catalogName = rs.getString("name");

                return Optional.of(new Catalog(catalogId, catalogName));

            }
        }
        return Optional.empty();
    }

    public List<Catalog> findAll() throws SQLException {
        List<Catalog> catalogs = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(
                "select id, name from cataloges;")){
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                catalogs.add(new Catalog(rs.getLong(1), rs.getString(2)));

            }
        }
        return catalogs;
    }

}
