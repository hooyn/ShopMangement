package hyjung.shop_management.repository;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;

import java.util.List;

public interface ItemRepository {
    void save(Item item);

    Item findById(Long id);

    List<Item> findAll();
}
