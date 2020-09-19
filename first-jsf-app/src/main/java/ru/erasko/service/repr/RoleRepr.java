package ru.erasko.service.repr;

import ru.erasko.persist.entity.Role;

import java.io.Serializable;

public class RoleRepr implements Serializable {

    private Long id;
    private String name;

    public RoleRepr() {
    }

    public RoleRepr(Role role) {
        this.id = role.getId();
        this.name = role.getName();
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
}
