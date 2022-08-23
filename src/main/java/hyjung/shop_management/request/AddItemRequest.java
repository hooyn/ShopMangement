package hyjung.shop_management.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class AddItemRequest {
    @ApiModelProperty(example = "아이템 이름")
    private String name;

    @ApiModelProperty(example = "아이템 가격")
    private Integer price;

    @ApiModelProperty(example = "아이템 재고")
    private Integer stockQuantity;
}
