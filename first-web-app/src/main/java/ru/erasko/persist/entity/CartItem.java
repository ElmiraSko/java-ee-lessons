package ru.erasko.persist.entity;

import java.math.BigDecimal;

public class CartItem {
    private Long id;
    private String prodName;
    private int qty;
    private BigDecimal price;

    public CartItem() {
    }

    public CartItem(Long id, String prodName, int qty, BigDecimal price) {
        this.id = id;
        this.prodName = prodName;
        this.qty = qty;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
