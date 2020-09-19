package ru.erasko.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoleRepository {

    private final Logger logger = LoggerFactory.getLogger(RoleRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public RoleRepository() {
    }

    public void insert(Role role) {
        logger.info("Inserting new role");
        em.persist(role);
    }

    public void update(Role role) {
        em.merge(role);
    }

    public void delete(Long id){
        Role role = em.find(Role.class, id);
        if (role != null) {
            em.remove(role);
        }
    }

    public Optional<Role> findById(Long id) {
        Role role = em.find(Role.class, id);
        if (role != null) {
            return Optional.of(role);
        }
        return Optional.empty();
    }

    public Role findByName(String name) {
        return em.createQuery("from Role where name = ?1", Role.class)
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<Role> findAll() {
        return em.createQuery("from Role", Role.class)
                .getResultList();
    }
}
