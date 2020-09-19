package ru.erasko.service.interf;

import ru.erasko.service.repr.RoleRepr;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface RoleService {

    void insert(RoleRepr roleRepr);

    void delete(Long id);

    Optional<RoleRepr> findById(Long id);

    List<RoleRepr> findAll();
}
