package iiqcov.blog.springbootdeveloper.config;

import iiqcov.blog.springbootdeveloper.auth.jwt.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final TokenAuthenticationFilter jwtFilter;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring() // 인증기능이 어떤 경로를 무시할지 지시
                .requestMatchers(toH2Console()); // H2콘솔에 해당하는 경로 무시
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:3000"));
        configuration.setAllowedMethods(
                Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/article/{id}", "/articles").permitAll()
                .requestMatchers("/api/login", "/api/sign-up", "/api/logout").permitAll()
                .requestMatchers("/api/article", "/api/article/{id}", "/api/image").authenticated()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                .cors().configurationSource(configurationSource())
                .and()
                .csrf().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    // (비유) 관리자 생성 후 스프링 컨테이너 등록
    // 사용자 인증을 처리하는데 사용되는 인터페이스
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userService) throws Exception{
        // (비유) 관리자가 생산을 설정하기 위한 도구를 가져옴
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                // (비유) 관리자가 생산에 필요한 작업자 배치
                .userDetailsService(userService)
                // (비유) 관리자가 생산에 필요한 장비 배치 : 비밀번호 암호화
                .passwordEncoder(bCryptPasswordEncoder)
                // (비유) 생산라인 자동화
                .and()
                .build();
    }
}
