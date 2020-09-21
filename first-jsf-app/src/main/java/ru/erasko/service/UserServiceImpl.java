package ru.erasko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.persist.RoleRepository;
import ru.erasko.persist.UserRepository;
import ru.erasko.persist.entity.Role;
import ru.erasko.persist.entity.User;
import ru.erasko.service.interf.UserService;
import ru.erasko.service.repr.UserRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @EJB
    private UserRepository userRepository;

    @EJB
    private RoleRepository roleRepository;


    @TransactionAttribute
    @Override
    public void insert(UserRepr userRepr) {
        List<Role> roles = new ArrayList<>();

        List<Long> ss = userRepr.getSelectedRoleIds();
        if (ss!=null) {
            for (Long s : ss) {
                roles.add(roleRepository.findById(s).get());
            }
        }
       User user = new User(userRepr.getId(), userRepr.getName(),
                userRepr.getPassword(), roles);

        userRepository.insert(user);
    }

    @TransactionAttribute
    @Override
    public void update(UserRepr userRepr) {
        List<Role> roles = new ArrayList<>();

        List<Long> ss = userRepr.getSelectedRoleIds();
        if (ss!=null) {
            for (Long s : ss) {
                roles.add(roleRepository.findById(s).get());
            }
        }
        User user = new User(userRepr.getId(), userRepr.getName(),
                userRepr.getPassword(), roles);
        userRepository.update(user);
    }

    @TransactionAttribute
    @Override
    public void delete(Long id) {
        userRepository.delete(id);

    }

    @Override
    public Optional<UserRepr> findById(Long id) {
        return userRepository.findById(id)
                .map(UserRepr::new);
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepository.findAll()
                .stream().map(UserRepr::new)
                .collect(Collectors.toList());
    }
}
