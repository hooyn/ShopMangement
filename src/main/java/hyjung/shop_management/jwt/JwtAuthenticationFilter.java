package hyjung.shop_management.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // HTTP request에서 Authorization헤더에서 토큰을 가져오는 메서드입니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 토큰 검증을 진행합니다. 토큰이 있는지와 토큰이 만료되지 않았는지 확인합니다.
        if(token!=null && jwtTokenProvider.validateTokenExpiration(token)){

            // 토큰을 통해 Authentication 객체를 얻어오는 메서드입니다.
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            // Authentication 객체를 인증된 사용자 정보를 저장하는 곳에 저장
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }
}
