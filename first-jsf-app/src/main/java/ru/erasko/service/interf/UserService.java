package ru.erasko.service.interf;

import ru.erasko.service.repr.UserRepr;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface UserService {

    void insert(UserRepr userRepr);

    void update(UserRepr userRepr);

    void delete(Long id);

    Optional<UserRepr> findById(Long id);

    List<UserRepr> findAll();
}
