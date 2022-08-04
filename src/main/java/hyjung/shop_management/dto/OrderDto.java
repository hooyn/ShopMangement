package hyjung.shop_management.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDto {
    private Long order_id;
    private Long member_id;
    private String username;
    private Long item_id;
    private String itemname;
    private LocalDateTime orderDate;
    private Integer totalPrice;

    public OrderDto(Long order_id, Long member_id, String username, Long item_id, String itemname, LocalDateTime orderDate, Integer totalPrice) {
        this.order_id = order_id;
        this.member_id = member_id;
        this.username = username;
        this.item_id = item_id;
        this.itemname = itemname;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}


