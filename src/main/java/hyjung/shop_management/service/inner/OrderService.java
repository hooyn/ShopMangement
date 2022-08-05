package hyjung.shop_management.service.inner;

import hyjung.shop_management.domain.Order;
import hyjung.shop_management.response.ApiResponse;

import java.util.Optional;

public interface OrderService {

    ApiResponse saveOrder(Long member_id, Long item_id, Integer count);

    Optional<Order> findOrderById(Long orderId);

    ApiResponse findOrderAll();

    ApiResponse findOrderByToken(String token);
}
