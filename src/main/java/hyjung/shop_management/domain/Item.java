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

}
