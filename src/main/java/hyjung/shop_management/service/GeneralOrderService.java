package hyjung.shop_management.service;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;
import hyjung.shop_management.domain.Order;
import hyjung.shop_management.domain.OrderItem;
import hyjung.shop_management.dto.OrderDto;
import hyjung.shop_management.jwt.JwtTokenProvider;
import hyjung.shop_management.repository.ItemRepository;
import hyjung.shop_management.repository.MemberRepository;
import hyjung.shop_management.repository.OrderRepository;
import hyjung.shop_management.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralOrderService implements OrderService{

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public ApiResponse saveOrder(Long member_id, Long item_id, Integer count) {
        if(member_id!=null && item_id!=null && count!=null){
            Member member = memberRepository.findById(member_id);
            if(member!=null){
                Item item = itemRepository.findById(item_id);
                if(item!=null){
                    if(item.getStockQuantity()>0){
                        boolean check = item.subQuantity(count);
                        if(check){
                            OrderItem orderItem = new OrderItem(item.getPrice()*count, count, item);
                            Order order = new Order(member, orderItem);

                            order.addOrderItem(orderItem);
                            Long id = orderRepository.save(order);
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

    @Override
    @Transactional(readOnly = true)
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse findOrderAll() {
        List<Order> order = orderRepository.findAll();

        List<OrderDto> data = order.stream().map(o -> {
            return new OrderDto(
                    o.getId(),
                    o.getMember().getId(),
                    o.getMember().getUsername(),
                    o.getOrderItems().get(0).getItem().getId(),
                    o.getOrderItems().get(0).getItem().getName(),
                    o.getOrderDate(),
                    o.getOrderItems().get(0).getOrderPrice());
        }).collect(Collectors.toList());

        return new ApiResponse(true, HttpStatus.OK.value(), data, "주문 내역을 조회 하였습니다.");
    }

    @Override
    public ApiResponse findOrderByToken(String token) {
        String userId = jwtTokenProvider.getUserId(token);

        List<Order> byUserId = orderRepository.findByUserId(userId);
        return new ApiResponse(true, HttpStatus.OK.value(), byUserId, "사용자별 주문 내역을 조회 하였습니다.");

    }
}
