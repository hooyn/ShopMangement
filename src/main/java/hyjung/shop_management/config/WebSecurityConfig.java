package hyjung.shop_management.config;

import hyjung.shop_management.jwt.JwtAuthenticationFilter;
import hyjung.shop_management.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests().mvcMatchers("/login", "/api/user", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                //JWTAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 등록을 해줍니다.
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico");

        // swagger
        web.ignoring().antMatchers(
                "/v2/api-docs",  "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**","/swagger/**");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    [NOT_USE]
    Custom AuthenticationProvider 빈 설정 -> 인증에 대한 일을 시킬 AuthenticationProvider 커스텀하여 등록

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new FormAuthenticationProvider();
    }
    */

}