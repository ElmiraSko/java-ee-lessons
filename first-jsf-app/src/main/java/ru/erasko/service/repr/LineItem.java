package ru.erasko.service.repr;

import java.math.BigDecimal;

public class LineItem {

    private Long id;
    private String name;
    private BigDecimal price;
    //======================
    private int qty = 1;
    private BigDecimal totalPrice;

    public LineItem() {
    }

    public LineItem(ProductRepr productRepr) {
        this.id = productRepr.getId();
        this.name = productRepr.getName();
        this.price = productRepr.getPrice();
        checkTotalPrice(qty); // считаем итоговую сумму
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int q) {
        this.qty = q;
        checkTotalPrice(qty); // пересчитываем итоговую сумму
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void checkTotalPrice(int qty) { //
        this.totalPrice = price.multiply(new BigDecimal(qty));
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
