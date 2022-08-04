package hyjung.shop_management.controller;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;
import hyjung.shop_management.domain.Order;
import hyjung.shop_management.domain.OrderItem;
import hyjung.shop_management.request.OrderItemRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.ItemService;
import hyjung.shop_management.service.MemberService;
import hyjung.shop_management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @PostMapping("/api/order")
    public ApiResponse orderItem(@RequestBody OrderItemRequest request){
        Long member_id = request.getMember_id();
        Long item_id = request.getItem_id();
        Integer count = request.getCount();

        if(member_id!=null && item_id!=null && count!=null){
            Member member = memberService.findMemberById(member_id);
            if(member!=null){
                Item item = itemService.findItemById(item_id);
                if(item!=null){
                    if(item.getQuantity()>0){
                        boolean check = item.subQuantity(count);
                        if(check){
                            OrderItem orderItem = OrderItem.builder().item(item).orderPrice(item.getPrice() * count).count(count).build();

                            Order order = Order.builder().member(member).orderDate(LocalDateTime.now()).build();
                            order.addOrderItem(orderItem);

                            Long id = orderService.saveOrder(order);
                            return new ApiResponse(true, HttpStatus.OK.value(), id, "주문이 접수되었습니다.");
                        } else {
                            return new ApiResponse(false, HttpStatus.NOT_FOUND.value(), null, "아이템의 재고가 없습니다.");
                        }
                    } else {
                        return new ApiResponse(false, HttpStatus.NOT_FOUND.value(), null, "아이템의 재고가 없습니다.");
                    }
                } else {
                    return new ApiResponse(false, HttpStatus.NOT_FOUND.value(), null, "등록되어 있지 않은 아이템입니다.");
                }
            } else {
                return new ApiResponse(false, HttpStatus.NOT_FOUND.value(), null, "등록되어 있지 않은 회원입니다.");
            }
        } else {
            return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "필수 입력사항을 입력해주세요.");
        }
    }
}
