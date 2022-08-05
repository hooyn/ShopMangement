package hyjung.shop_management.service;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.dto.ItemDto;
import hyjung.shop_management.repository.ItemRepository;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.inner.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralItemService implements ItemService {

    private final ItemRepository itemRepository;

    //Long saveItem(Item item);
    @Override
    @Transactional
    public ApiResponse saveItem(String name, Integer stockQuantity, Integer price) {
        if(!name.isBlank() && stockQuantity!=null && price!=null){
            Item item = new Item(name, price, stockQuantity);

            Long id = itemRepository.save(item).getId();
            return new ApiResponse(true, HttpStatus.OK.value(), id, "아이템이 등록되었습니다.");
        } else {
            return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "필수 입력사항을 입력해주세요.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> findItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse findItemAll() {
        List<Item> item = itemRepository.findAll();

        List<ItemDto> data = item.stream().map(i -> {
            return new ItemDto(i.getId(), i.getName(), i.getPrice(), i.getStockQuantity());
        }).collect(Collectors.toList());

        return new ApiResponse(true, HttpStatus.OK.value(), data, "아이템이 조회되었습니다.");
    }
}
