package hyjung.shop_management.jwt.notuse;

import hyjung.shop_management.jwt.MemberDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String userPw = (String) authentication.getCredentials();
        MemberDetail memberDetail = (MemberDetail) userDetailsService.loadUserByUsername(userId);

        if(!passwordEncoder.matches(userPw, memberDetail.getPassword())){
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }
        return new UsernamePasswordAuthenticationToken(userId, userPw, memberDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // AuthenticationProvider 구현체는 인증을 진행할 때 인증 정보를 담은 Authentication 객체를 가지고 인증을
        // 진행하는데 이 Authentication 객체는 AuthenticationProvider마다 다르기 때문에 support()를 통해
        // Authentication객체에 맞는 AuthenticationProvider를 찾습니다.
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
