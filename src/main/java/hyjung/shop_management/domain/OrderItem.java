package hyjung.shop_management.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "order_price")
    private Integer orderPrice;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Integer orderPrice, Integer count, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.item = item;
    }

    public void setOrder(Order order){
        this.order = order;
    }
}
