package ru.erasko.service.interf;

import ru.erasko.persist.entity.Order;
import ru.erasko.service.repr.OrderRepr;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {

    List<OrderRepr> getAllOrders();

    void update(OrderRepr orderRepr);

    void insert(OrderRepr orderRepr);

    void deleteOrder(Long id);

}
