package hyjung.shop_management.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    // 토큰 만료 시간 설정합니다.
    private Long ExpiredTime = 1000L * 60 * 15;

    // Refresh 토큰 만료 시간 설정합니다.
    private Long RefreshTime = 1000L * 60 * 60 * 24 * 7;

    // 서명 암호화 알고리즘 설정합니다.
    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    //사용자의 정보를 받아오는 서비스 클래스
    private final UserDetailsService memberDetailService;

    // 토큰 생성합니다.
    public String createToken(String userId){
        Claims claims = Jwts.claims().setSubject(userId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ExpiredTime))
                .signWith(algorithm, secretKey)
                .compact();
    }

    // 리프레시 토큰 생성합니다.
    public String createRefreshToken(){
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+RefreshTime))
                .signWith(algorithm, secretKey)
                .compact();
    }

    // Authentication 인증 객체 생성하여 반환합니다.
    public Authentication getAuthentication(String token){
        // 사용자의 정보를 반환합니다.
        UserDetails userDetails = memberDetailService.loadUserByUsername(getUserId(token));

        // 사용자 정보인 userDetails를 통해 Authentication 객체를 반환합니다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰을 통해서 사용자의 아이디를 받습니다.
    public String getUserId(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }

    // 토큰이 만료되었는지 확인합니다.
    public boolean validateTokenExpiration(String token){
        try{
            // 토큰의 Claims를 받아오는 코드를 실행하고 만약 토큰이 만료되었거나, 잘못된 토큰이라면 에러가 반환될 것이기 때문에
            // 에러가 반환되지 않았다면 만료되지 않은 토큰으로 판단합니다.
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request){
        // Http Header에 Authorization 값을 가져옵니다.
        // 원래 Bearer을 앞에 붙여주어야합니다.
        // 이 코드에서만 Bearer을 제외시키고 코드를 구성하였습니다.
        return request.getHeader("Authorization");
    }
}
