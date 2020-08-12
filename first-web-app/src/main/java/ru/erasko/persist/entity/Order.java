package ru.erasko.persist.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Order {
    private Long id;
    private Long customerId;
    private int qty;
    private BigDecimal totalPrice;
    private Date date;

    public Order() {
    }

    public Order(Long id, Long customerId, int qty, BigDecimal totalPrice, Date date) {
        this.id = id;
        this.customerId = customerId;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public Order(Long id, Long customerId, int qty, BigDecimal totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.qty = qty;
        this.totalPrice = totalPrice;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
}
