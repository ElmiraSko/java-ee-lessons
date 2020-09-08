package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.erasko.service.interf.CartService;
import ru.erasko.service.interf.OrderService;
import ru.erasko.service.repr.OrderRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@SessionScoped
@Named
public class OrderController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @EJB
    private OrderService orderService;
    @EJB
    private CartService cartService;

    private OrderRepr orderRepr;

    public OrderRepr getOrder() {
        return orderRepr;
    }

    public void setOrder(OrderRepr orderRepr) {
        this.orderRepr = orderRepr;
    }
//==============================================================

    public List<OrderRepr> getAllOrders() {
        return orderService.getAllOrders();
    }

    // формируем заказ
    public String createOrder() {
        int id = cartService.getSessionId();
        logger.info("OrderController -> createOrder() -> SessionId() " + id);

        this.orderRepr = new OrderRepr();
        orderRepr.setCustomerId((long) id);
        orderRepr.setTotalPrice(cartService.cartTotalSum());
        orderRepr.setDate(new Date(System.currentTimeMillis()));
     // сохраняем заказ в БД и переходим на страницу заказов
        saveOrder(orderRepr);
        return "/orders.xhtml?faces-redirect=true";
    }

    public String editOrder(OrderRepr orderRepr) {
        this.orderRepr = orderRepr;
        return "/order-form.xhtml?faces-redirect=true";
    }

    public void deleteOrder(OrderRepr orderRepr) {
        orderService.deleteOrder(orderRepr.getId());
    }

    public void saveOrder(OrderRepr orderRepr) {
        if (orderRepr.getId() != null) {
            orderService.update(orderRepr);
        } else {
            orderRepr.setDate(new Date(System.currentTimeMillis()));
            orderService.insert(orderRepr);
        }
    }

    public String saveOrder() {
        if (orderRepr.getId() != null) {
            orderService.update(orderRepr);
        } else {
            orderRepr.setDate(new Date(System.currentTimeMillis()));
            orderService.insert(orderRepr);
        }
        return "/orders.xhtml?faces-redirect=true";
    }
}
