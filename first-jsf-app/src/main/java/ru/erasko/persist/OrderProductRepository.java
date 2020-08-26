package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Category;
import ru.erasko.persist.entity.Order;
import ru.erasko.persist.entity.OrderProduct;
import ru.erasko.persist.entity.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named
public class OrderProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public OrderProductRepository() {
    }

    @Transactional
    public void insert(OrderProduct orderProduct) {
        em.persist(orderProduct);
    }

    @Transactional
    public void update(OrderProduct orderProduct) {
        em.merge(orderProduct);
    }

    @Transactional
    public void delete(long id) {
        OrderProduct orderProduct = em.find(OrderProduct.class, id);
        if (orderProduct != null) {
            em.remove(orderProduct);
        }
    }

    public Optional<OrderProduct> findById(long id) {
        OrderProduct orderProduct = em.find(OrderProduct.class, id);
        if (orderProduct != null) {
            return Optional.of(orderProduct);
        }
        return Optional.empty();
    }

    public List<OrderProduct> findAll() {
        return em.createQuery("from OrderProduct", OrderProduct.class)
                .getResultList();
    }
}
