package hyjung.shop_management.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer quantity;

    public void addQuantity(int count){
        this.quantity += count;
    }

    public boolean subQuantity(int count){
        int result = this.quantity - count;
        if(result<0){
            return false;
        } else{
            this.quantity -= count;
            return true;
        }
    }
}
