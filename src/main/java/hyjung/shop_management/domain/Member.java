package hyjung.shop_management.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_pw")
    private String userPw;
    private String username;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member(String userId, String userPw, String username) {
        this.userId = userId;
        this.userPw = userPw;
        this.username = username;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.userPw = passwordEncoder.encode(userPw);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password, getUserPw());
    }
}
