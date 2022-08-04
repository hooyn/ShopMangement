package hyjung.shop_management.request;

import lombok.Getter;

@Getter
public class AddItemRequest {
    private String name;
    private Integer price;
    private Integer stockQuantity;
}
