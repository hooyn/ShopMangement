package hyjung.shop_management.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String userPw;
    private String username;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;
}
