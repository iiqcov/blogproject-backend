package iiqcov.blog.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring() // 인증기능이 어떤 경로를 무시할지 지시
                .requestMatchers(toH2Console()) // H2콘솔에 해당하는 경로 무시
                .requestMatchers("/api/**", "/articles", "/articles/{id}");
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
