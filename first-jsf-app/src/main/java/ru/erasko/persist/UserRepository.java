package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public UserRepository() {
    }

    public void insert(User user) {
        logger.info("Inserting new user");
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(Long id){
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User findByName(String name) {
        return em.createQuery("from User where name = ?1", User.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<User> findAll() {
        return em.createQuery("from User", User.class)
                .getResultList();
    }
}
