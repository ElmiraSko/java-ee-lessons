package ru.erasko.service;

import ru.erasko.persist.UserRepository;
import ru.erasko.persist.entity.User;
import ru.erasko.service.interf.UserService;
import ru.erasko.service.repr.UserRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class UserServiceImpl implements UserService {
    @EJB
    private UserRepository userRepository;

    @TransactionAttribute
    @Override
    public void insert(UserRepr userRepr) {
        User user = new User(userRepr.getId(), userRepr.getName(), userRepr.getPassword());
        userRepository.insert(user);
    }

    @TransactionAttribute
    @Override
    public void update(UserRepr userRepr) {
        User user = new User(userRepr.getId(), userRepr.getName(), userRepr.getPassword());
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
