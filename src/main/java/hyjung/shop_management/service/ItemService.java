package hyjung.shop_management.service;

import hyjung.shop_management.domain.Item;

import java.util.List;

public interface ItemService {

    Long saveItem(Item item);

    Item findItemById(Long itemId);

    List<Item> findItemAll();
}
