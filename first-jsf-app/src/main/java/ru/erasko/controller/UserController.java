package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.RoleService;
import ru.erasko.service.interf.UserService;
import ru.erasko.service.repr.RoleRepr;
import ru.erasko.service.repr.UserRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class UserController implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @EJB
    private UserService userService;

    @EJB
    private RoleService roleService;

    private UserRepr userRepr;

    public  List<RoleRepr> getAllRoles() {
     return roleService.findAll();
    }

    public UserRepr getUserRepr() {
        return userRepr;
    }

    public void setUserRepr(UserRepr userRepr) {
        this.userRepr = userRepr;
    }

    public List<UserRepr> getAllUsers(){
        logger.info("logger getAllUsers ");
        return userService.findAll();
    }

    public String createUser() {
        this.userRepr = new UserRepr();
        return "/user-form.xhtml?faces-redirect=true";
    }

    public String editUser(UserRepr userRepr) {
        this.userRepr = userRepr;
        return "/user-form.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserRepr userRepr) {
        userService.delete(userRepr.getId());
    }

    public String saveUser() {
        if (userRepr.getId() != null) {
            userService.update(userRepr);
        } else {
            userService.insert(userRepr);
        }
        return "/users.xhtml?faces-redirect=true";
    }
}
