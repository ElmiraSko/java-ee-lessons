package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Category;
import ru.erasko.persist.entity.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProductRepository {
    private final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

//    @Resource
//    private UserTransaction ut;

    public ProductRepository() {
    }

    public void insert(Product product) {
        logger.info("Inserting new product");
        em.persist(product);
    }

    public void update(Product product) {
        em.merge(product);
    }

    public void delete(Long id){
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }

    public Optional<Product> findById(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public Product findByName(String name) {
        return em.createQuery("from Product where name = ?1", Product.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<Product> findProductByCategoryId(Long id) {
        Category category = em.find(Category.class, id);
        return em.createQuery("from Product where category = ?1", Product.class)
                .setParameter(1, category)
                .getResultList();

    }

    public List<Product> findAll() {
        return em.createQuery("from Product", Product.class)
                .getResultList();
    }

}
