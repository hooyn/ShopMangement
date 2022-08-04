package hyjung.shop_management.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private Integer orderPrice;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
}
