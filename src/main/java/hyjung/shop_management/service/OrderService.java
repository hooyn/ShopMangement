package hyjung.shop_management.service;

import hyjung.shop_management.domain.Order;

import java.util.List;

public interface OrderService {

    Long saveOrder(Order order);

    Order findOrderById(Long orderId);

    List<Order> findOrderAll();
}
