package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Order;

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
public class OrderRepository {

    private final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Inject
    private UserTransaction ut;

    public OrderRepository() {
    }

    @PostConstruct
    public void init() {
        if (this.findAll().isEmpty()) {
            logger.info("No order in DB. Initialized");

            try {
                ut.begin();
            this.insert(new Order(null, 3L,  new BigDecimal(13400)));
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
    public void insert(Order order) {
       em.persist(order);
    }

    @Transactional
    public void update(Order order) {
        em.merge(order);
    }

    @Transactional
    public void delete(Long id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            em.remove(order);
        }
    }

    public Optional<Order> findById(Long id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public List<Order> findAll() {
        return em.createQuery("from Order", Order.class)
                .getResultList();
    }

}
