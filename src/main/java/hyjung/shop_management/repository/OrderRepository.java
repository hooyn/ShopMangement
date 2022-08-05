package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Order;

import java.util.List;

public interface OrderRepository {
    Long save(Order order);
    Order findById(Long id);
    List<Order> findAll();

    List<Order> findByUserId(String userId);
}