package hyjung.shop_management.controller;

import hyjung.shop_management.request.AddItemRequest;
import hyjung.shop_management.response.ApiResponse;
import hyjung.shop_management.service.inner.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/api/item")
    public ApiResponse AddItem(@RequestBody AddItemRequest request){
        String name = request.getName();
        Integer stockQuantity = request.getStockQuantity();
        Integer price = request.getPrice();

        return itemService.saveItem(name, stockQuantity, price);
    }

    @GetMapping("/api/item")
    public ApiResponse selectItemAll(){
        return itemService.findItemAll();
    }
}
