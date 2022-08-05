package hyjung.shop_management.service;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.response.ApiResponse;

import java.util.List;

public interface ItemService {

    ApiResponse saveItem(String name, Integer stockQuantity, Integer price);

    Item findItemById(Long itemId);

    ApiResponse findItemAll();
}
