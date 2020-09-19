package ru.erasko.service.repr;

import ru.erasko.persist.entity.Role;
import ru.erasko.persist.entity.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserRepr {

    private Long id;
    private String name;
    private String password;
    Map<Long, String> roles = new HashMap<>();

    public UserRepr() {
    }

    public UserRepr(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        if (user.getRoles() != null) {
            for (Role r : user.getRoles()) {
                roles.put(r.getId(), r.getName());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Long, String> getRoles() {
        return roles;
    }

    public void setRoles(Map<Long, String> roles) {
        this.roles = roles;
    }
    public Set<String> getRolesName() {
        return new HashSet<>(roles.values());
    }
}
