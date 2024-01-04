package iiqcov.blog.springbootdeveloper.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // application.yml에 있는 jwt 설정 정보 가져오기
public class JwtProperties {
    // jwt를 발행한 주체 - 나름의 식별자
    private String issuer;
    // jwt를 암호화하고 복호화 하는데 사용되는 비밀 키
    private String secretKey;
}
