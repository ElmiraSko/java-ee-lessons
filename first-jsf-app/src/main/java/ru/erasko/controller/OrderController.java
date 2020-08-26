package ru.erasko.controller;

import ru.erasko.persist.OrderRepository;
import ru.erasko.persist.entity.Order;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@SessionScoped
@Named
public class OrderController implements Serializable {
    @Inject
    private OrderRepository orderRepository;
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public String createOrder() {
        this.order = new Order();
        return "/order-form.xhtml?faces-redirect=true";
    }

    public String editOrder(Order order) {
        this.order = order;
        return "/order-form.xhtml?faces-redirect=true";
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order.getId());
    }

    public String saveOrder() {
        if (order.getId() != null) {
            orderRepository.update(order);
        } else {
            order.setDate(new Date(System.currentTimeMillis()));
            orderRepository.insert(order);
        }
        return "/orders.xhtml?faces-redirect=true";
    }
}
