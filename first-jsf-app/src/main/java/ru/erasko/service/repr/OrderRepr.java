package ru.erasko.service.repr;

import ru.erasko.persist.entity.Order;
import ru.erasko.persist.entity.OrderProduct;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderRepr {

    private Long id;
    private Long customerId;
    private BigDecimal totalPrice;
    private Date date;
    private List<OrderProduct> orderProducts;

    public OrderRepr() {
    }

    public OrderRepr(Long id, Long customerId,
                     BigDecimal totalPrice, Date date,
                     List<OrderProduct> orderProducts) {
        this.id = id;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.orderProducts = orderProducts;
    }

    public OrderRepr(Order order) {
        this.id = order.getId();
        this.customerId = order.getCustomerId();
        this.totalPrice = order.getTotalPrice();
        this.date = order.getDate();
        this.orderProducts = order.getOrderProducts();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
