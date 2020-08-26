package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.CartItem;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class CartItemRepository {
    private final Logger logger = LoggerFactory.getLogger(CartItemRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    public CartItemRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {

        if (this.findAll().isEmpty()) {
            logger.info("No cartItem in DB. Initialized");
            try {
                ut.begin();
            this.insert(new CartItem(null, "Samsung Galaxy Tab S7", 2, new BigDecimal(13400)));
                ut.commit();
            } catch (Exception ex) {
                logger.error("", ex);
                try {
                    ut.rollback();
                } catch (SystemException e) {
                    logger.error("", e);
                }
            }
        }
    }

    @Transactional
    public void insert(CartItem cartItem) throws SQLException {
        em.persist(cartItem);
    }

    @Transactional
    public void update(CartItem cartItem) throws SQLException {
        em.merge(cartItem);
    }

    @Transactional
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
