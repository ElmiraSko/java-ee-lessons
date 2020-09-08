package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.OrderProduct;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrderProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrderProductRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public OrderProductRepository() {
    }

    @TransactionAttribute
    public void insert(OrderProduct orderProduct) {
        em.persist(orderProduct);
    }

    @TransactionAttribute
    public void update(OrderProduct orderProduct) {
        em.merge(orderProduct);
    }

    @TransactionAttribute
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
