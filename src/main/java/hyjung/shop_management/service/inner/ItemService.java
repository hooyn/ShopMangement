package hyjung.shop_management.service.inner;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.response.ApiResponse;

import java.util.Optional;

public interface ItemService {

    ApiResponse saveItem(String name, Integer stockQuantity, Integer price);

    Optional<Item> findItemById(Long itemId);

    ApiResponse findItemAll();
}
