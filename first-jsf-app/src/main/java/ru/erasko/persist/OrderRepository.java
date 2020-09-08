package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Order;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrderRepository {

    private final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public OrderRepository() {
    }

    @TransactionAttribute
    public void insert(Order order) {
       em.persist(order);
    }

    @TransactionAttribute
    public void update(Order order) {
        em.merge(order);
    }

    @TransactionAttribute
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
