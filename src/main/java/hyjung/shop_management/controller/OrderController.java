package hyjung.shop_management.controller;

import hyjung.shop_management.request.OrderItemRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.inner.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/order")
    public ApiResponse orderItem(@RequestBody OrderItemRequest request){
        Long member_id = request.getMember_id();
        Long item_id = request.getItem_id();
        Integer count = request.getCount();

        return orderService.saveOrder(member_id, item_id, count);
    }

    @GetMapping("/api/order")
    public ApiResponse selectOrderAll(){
        return orderService.findOrderAll();
    }

    @GetMapping("/api/token")
    public ApiResponse selectOrderToken(@RequestHeader(value = "Authorization") String token){
        return orderService.findOrderByToken(token);
    }
}
