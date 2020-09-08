package ru.erasko.service;

import ru.erasko.persist.OrderRepository;
import ru.erasko.persist.entity.Order;
import ru.erasko.service.interf.OrderService;
import ru.erasko.service.repr.OrderRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderServiceImpl implements OrderService {

    @EJB
    private OrderRepository orderRepository;

    @Override
    public List<OrderRepr> getAllOrders() {
            return orderRepository.findAll()
            .stream()
            .map(OrderRepr :: new)
            .collect(Collectors.toList());
    }

    @Override
    public void update(OrderRepr orderRepr) {
        Order order = new Order(orderRepr.getId(),
                orderRepr.getCustomerId(),
                orderRepr.getTotalPrice());
        orderRepository.update(order);
    }

    @Override
    public void insert(OrderRepr orderRepr) {
        Order order = new Order(orderRepr.getId(),
                orderRepr.getCustomerId(),
                orderRepr.getTotalPrice());
        orderRepository.insert(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.delete(id);
    }
}
