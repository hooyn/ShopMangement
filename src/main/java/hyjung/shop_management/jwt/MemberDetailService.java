package hyjung.shop_management.jwt;

import hyjung.shop_management.domain.Member;
import hyjung.shop_management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        // 데이터베이스에서 사용자를 찾아서 엔티티 반환합니다.
        Member member = memberRepository.findByUserId(userId);

        // 역할을 저장하기 위해서 리스트를 만들어줍니다.
        List<GrantedAuthority> roles = new ArrayList<>();

        // 역할에 member의 역할에 대한 내용을 넣어줍니다.
        roles.add(new SimpleGrantedAuthority(member.getRole().toString()));

        // MemberDetail을 생성하여 반환합니다.
        return MemberDetail.builder()
                .userId(member.getUserId())
                .userPw(member.getUserPw())
                .authorities(roles)
                .build();
    }
}
