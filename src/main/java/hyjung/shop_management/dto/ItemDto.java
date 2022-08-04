package hyjung.shop_management.dto;

import lombok.Getter;

@Getter
public class ItemDto {
    private Long item_id;
    private String name;
    private Integer price;
    private Integer quantity;

    public ItemDto(Long item_id, String name, Integer price, Integer quantity) {
        this.item_id = item_id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
