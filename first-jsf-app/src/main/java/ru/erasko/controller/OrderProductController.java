package ru.erasko.controller;

import ru.erasko.persist.OrderProductRepository;
import ru.erasko.persist.entity.OrderProduct;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class OrderProductController implements Serializable {
    @Inject
    private OrderProductRepository orderProductRepository;
    private OrderProduct orderProduct;

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public List<OrderProduct> getAllOrderProduct() {
        return orderProductRepository.findAll();
    }

    public String createOrderProduct() {
        this.orderProduct = new OrderProduct();
        return "/orderProduct-form.xhtml?faces-redirect=true";
    }

    public String editOrderProduct(OrderProduct order) {
        this.orderProduct = order;
        return "/orderProduct-form.xhtml?faces-redirect=true";
    }

    public void deleteOrderProduct(OrderProduct order) {
        orderProductRepository.delete(order.getId());
    }

    public String saveOrderProduct() {
        if (orderProduct.getId() != null) {
            orderProductRepository.update(orderProduct);
        } else {
            orderProductRepository.insert(orderProduct);
        }
        return "/orderProduct.xhtml?faces-redirect=true";
    }
}

