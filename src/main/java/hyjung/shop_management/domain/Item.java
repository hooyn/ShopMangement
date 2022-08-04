package hyjung.shop_management.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;

    public Item(String name, Integer price, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addQuantity(int count){
        this.stockQuantity += count;
    }

    public boolean subQuantity(int count){
        int result = this.stockQuantity - count;
        if(result<0){
            return false;
        } else{
            this.stockQuantity -= count;
            return true;
        }
    }
}
