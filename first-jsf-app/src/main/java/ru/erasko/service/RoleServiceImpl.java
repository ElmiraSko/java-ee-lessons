package ru.erasko.service;

import ru.erasko.persist.RoleRepository;
import ru.erasko.persist.entity.Role;
import ru.erasko.service.interf.RoleService;
import ru.erasko.service.repr.RoleRepr;
import ru.erasko.service.repr.UserRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class RoleServiceImpl implements RoleService {

    @EJB
    private RoleRepository roleRepository;

    @TransactionAttribute
    @Override
    public void insert(RoleRepr roleRepr) {
        Role role = new Role(roleRepr.getId(), roleRepr.getName());
        roleRepository.insert(role);
    }

    @TransactionAttribute
    @Override
    public void delete(Long id) {
        roleRepository.delete(id);
    }

    @Override
    public Optional<RoleRepr> findById(Long id) {
        return roleRepository.findById(id)
                .map(RoleRepr::new);
    }

    @Override
    public List<RoleRepr> findAll() {
        return roleRepository.findAll()
                .stream().map(RoleRepr::new)
                .collect(Collectors.toList());
    }
}
