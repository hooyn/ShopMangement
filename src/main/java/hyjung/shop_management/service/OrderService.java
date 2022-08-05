package hyjung.shop_management.service;

import hyjung.shop_management.domain.Order;
import hyjung.shop_management.response.ApiResponse;

import java.util.List;

public interface OrderService {

    ApiResponse saveOrder(Long member_id, Long item_id, Integer count);

    Order findOrderById(Long orderId);

    ApiResponse findOrderAll();
}
