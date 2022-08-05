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
    private Long ExpiredTime = 1000L * 60 * 15;
    private Long RefreshTime = 1000L * 60 * 60 * 24 * 7;

    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private final UserDetailsService memberDetailService;

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

    public String createRefreshToken(){
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+RefreshTime))
                .signWith(algorithm, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = memberDetailService.loadUserByUsername(getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }

    public boolean validateTokenExpiration(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
}
