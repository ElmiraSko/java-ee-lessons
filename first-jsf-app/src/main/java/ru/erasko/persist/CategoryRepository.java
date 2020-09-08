package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Category;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;

@Stateless
public class CategoryRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public CategoryRepository() {
    }

    public void insert(Category category) {
        em.persist(category);
    }

    public void update(Category category) {
        em.merge(category);
    }

    public void delete(long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    public Optional<Category> findById(long id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            return Optional.of(category);
        }
        return Optional.empty();
    }

    public List<Category> findAll() {
        return em.createQuery("from Category", Category.class)
                .getResultList();
    }

    public Optional<Category> findByName(String name) {
        Category category = em.createQuery("from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
        if (category != null) {
            return Optional.of(category);
        } else {
            return Optional.empty();
        }
    }
}
