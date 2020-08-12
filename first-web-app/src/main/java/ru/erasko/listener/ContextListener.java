package ru.erasko.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.CatalogRepository;
import ru.erasko.persist.OrderRepository;
import ru.erasko.persist.entity.CartItem;
import ru.erasko.persist.CartItemRepository;
import ru.erasko.persist.entity.Catalog;
import ru.erasko.persist.entity.Order;
import ru.erasko.persist.entity.Product;
import ru.erasko.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebListener
public class ContextListener implements ServletContextListener {

        private final Logger logger = LoggerFactory.getLogger(ContextListener.class);

        @Override
        public void contextInitialized(ServletContextEvent sce) {
                logger.info("Context initialization: ");

                ServletContext sc = sce.getServletContext();
                String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
                String username = sc.getInitParameter("username");
                String password = sc.getInitParameter("password");

                try {
                        Connection conn = DriverManager.getConnection(jdbcConnectionString, username, password);
                        sc.setAttribute("jdbcConnection", conn);

                        ProductRepository productRepository = new ProductRepository(conn);
                        sc.setAttribute("productRepository", productRepository);

                        CartItemRepository cartRepository = new CartItemRepository(conn);
                        sc.setAttribute("cartRepository", cartRepository);

                        CatalogRepository catalogRepository = new CatalogRepository(conn);
                        sc.setAttribute("catalogRepo", catalogRepository);

                        OrderRepository orderRepository = new OrderRepository(conn);
                        sc.setAttribute("orderRepository", orderRepository);

                        if (productRepository.findAll().isEmpty()) {
                                logger.info("No product in DB. Initialized");
                                productRepository.insert(new Product(-1L, "Samsung Galaxy Tab S7", "Samsung Galaxy Tab S7 description", new BigDecimal(13400)));
                        }

                        if (cartRepository.findAll().isEmpty()) {
                                logger.info("No cartItem in DB. Initialized");
                                cartRepository.insert(new CartItem(-1L, "Samsung Galaxy Tab S7", 1, new BigDecimal(13400)));
                        }

                        if (catalogRepository.findAll().isEmpty()) {
                                logger.info("No catalog in DB. Initialized");
                                catalogRepository.insert(new Catalog(-1L, "Smartphones"));
                        }

                        if (orderRepository.findAll().isEmpty()) {
                                logger.info("No orders in DB. Initialized");
                                orderRepository.insert(new Order(-1L, 1L, 2, new BigDecimal(13400), new Date(System.currentTimeMillis())));
                        }

                } catch (SQLException e) {
                        logger.error("", e);
                }
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
                ServletContext sc = sce.getServletContext();
                Connection conn = (Connection) sc.getAttribute("jdbcConnection");
                try {
                        if (conn != null && conn.isClosed()) {
                                conn.close();
                        }

                } catch (SQLException e) {
                        logger.error("", e);
                }
        }
}
