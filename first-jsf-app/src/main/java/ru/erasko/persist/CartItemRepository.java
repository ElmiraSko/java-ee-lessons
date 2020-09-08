package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.CartItem;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Stateless
public class CartItemRepository {
    private final Logger logger = LoggerFactory.getLogger(CartItemRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public CartItemRepository() {
    }

    @TransactionAttribute
    public void insert(CartItem cartItem) throws SQLException {
        em.persist(cartItem);
    }

    @TransactionAttribute
    public void update(CartItem cartItem) throws SQLException {
        em.merge(cartItem);
    }

    @TransactionAttribute
    public void delete(Long id) throws SQLException {
        CartItem cartItem = em.find(CartItem.class, id);
        if (cartItem != null) {
            em.remove(cartItem);
        }
    }

    public Optional<CartItem> findById(Long id) throws SQLException {
        CartItem cartItem = em.find(CartItem.class, id);
        if (cartItem != null) {
            return Optional.of(cartItem);
        }
        return Optional.empty();
    }

    public List<CartItem> findAll() throws SQLException {
        return em.createQuery("from CartItem", CartItem.class)
                .getResultList();
    }

}
