package ru.erasko.service.repr;

import ru.erasko.persist.entity.Role;
import ru.erasko.persist.entity.User;

import java.io.Serializable;
import java.util.*;

public class UserRepr implements Serializable {

    private Long id;
    private String name;
    private String password;

    private List<Long> selectedRoleIds; // для получения из представления
    private String[] strRoles; //для вывода в представлении

    public UserRepr() {
    }

    public UserRepr(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();

        if (user.getRoles() != null) {

            List<Role> rl = user.getRoles();

            strRoles = new String[rl.size()]; //

            for (int i = 0; i < rl.size(); i++) {
                strRoles[i] = rl.get(i).getName(); //
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

    public List<Long> getSelectedRoleIds() {
        return selectedRoleIds;
    }

    public void setSelectedRoleIds(List<Long> selectedRoleIds) {
        this.selectedRoleIds = selectedRoleIds;
    }

    public String getStrRoles() {
        return Arrays.toString(strRoles);
    }

    public void setStrRoles(String[] strRoles) {
        this.strRoles = strRoles;
    }
}
