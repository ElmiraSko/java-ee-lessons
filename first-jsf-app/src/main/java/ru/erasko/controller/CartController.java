package ru.erasko.controller;

import ru.erasko.persist.CartItemRepository;
import ru.erasko.persist.entity.CartItem;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class CartController implements Serializable {

    @Inject
    private CartItemRepository cartItemRepository;

    private CartItem cartItem;

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public List<CartItem> getAllCartItem() throws SQLException {
        return cartItemRepository.findAll();
    }

    public String createCartItem() {
        this.cartItem = new CartItem();
        return "/cart-form.xhtml?faces-redirect=true";
    }

    public String editCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
        return "/cart-form.xhtml?faces-redirect=true";
    }

    public void deleteCartItem(CartItem cartItem) throws SQLException {
        cartItemRepository.delete(cartItem.getId());
    }

    public String saveCartItem() throws SQLException {
        if (cartItem.getId() != null) {
            cartItemRepository.update(cartItem);
        } else {
            cartItemRepository.insert(cartItem);
        }
        return "/cart.xhtml?faces-redirect=true";
    }
}
