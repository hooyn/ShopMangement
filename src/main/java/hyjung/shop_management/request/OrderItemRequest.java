package hyjung.shop_management.request;

import lombok.Getter;

@Getter
public class OrderItemRequest {
    private Long member_id;
    private Long item_id;
    private Integer count;
}
