package hyjung.shop_management.service;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Long saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findItemAll() {
        return itemRepository.findAll();
    }
}
