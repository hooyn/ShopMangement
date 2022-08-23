package hyjung.shop_management.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class OrderItemRequest {
    @ApiModelProperty(example = "사용자 식별 ID")
    private Long member_id;

    @ApiModelProperty(example = "아이템 식별 ID")
    private Long item_id;

    @ApiModelProperty(example = "아이템 주문 개수")
    private Integer count;
}
