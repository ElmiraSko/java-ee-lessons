package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.CartService;
import ru.erasko.service.repr.LineItem;
import ru.erasko.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@SessionScoped
@Named
public class CartController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @EJB
    private CartService cartService;

   // метод addProductToCart вызывается на странице продуктов при добавлении продукта в корзину
    public void addProductToCart(ProductRepr productRepr) {
        LineItem lineItem = new LineItem(productRepr);
        addCartItem(lineItem);
    }

    public List<LineItem> getAllItem() {
        logger.info("CartController, getAllItem()");
        return cartService.getAllProduct();
    }

// addCartItem используется в методе addProductToCart(ProductRepr productRepr)
    public void addCartItem(LineItem lineItem) {
        cartService.addProduct(lineItem);
    }

    public void deleteCartItem(LineItem lineItem) {
        cartService.delete(lineItem);
        cartTotalSum();
    }

    public BigDecimal cartTotalSum() {
        return cartService.cartTotalSum();
    }
}
