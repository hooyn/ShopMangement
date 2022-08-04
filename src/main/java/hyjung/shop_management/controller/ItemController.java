package hyjung.shop_management.controller;

import hyjung.shop_management.domain.Item;
import hyjung.shop_management.domain.Member;
import hyjung.shop_management.dto.ItemDto;
import hyjung.shop_management.request.AddItemRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/api/item")
    public ApiResponse AddItem(@RequestBody AddItemRequest request){
        String name = request.getName();
        Integer quantity = request.getQuantity();
        Integer price = request.getPrice();

        if(!name.isBlank() && quantity!=null && price!=null){
            Item build = Item.builder()
                    .name(name)
                    .quantity(quantity)
                    .price(price)
                    .build();

            Long id = itemService.saveItem(build);
            return new ApiResponse(true, HttpStatus.OK.value(), id, "아이템이 등록되었습니다.");
        } else {
            return new ApiResponse(false, HttpStatus.BAD_REQUEST.value(), null, "필수 입력사항을 입력해주세요.");
        }
    }

    @GetMapping("/api/item")
    public ApiResponse selectItemAll(){
        List<Item> item = itemService.findItemAll();

        List<ItemDto> data = item.stream().map(i -> {
            return new ItemDto(i.getId(), i.getName(), i.getPrice(), i.getQuantity());
        }).collect(Collectors.toList());

        return new ApiResponse(true, HttpStatus.OK.value(), data, "아이템이 조회되었습니다.");

    }
}
