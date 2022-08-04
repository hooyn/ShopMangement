package hyjung.shop_management.service;

import hyjung.shop_management.domain.Order;
import hyjung.shop_management.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Long saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findOrderAll() {
        return orderRepository.findAll();
    }
}
