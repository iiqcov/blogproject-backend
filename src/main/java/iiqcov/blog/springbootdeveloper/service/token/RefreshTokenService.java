package iiqcov.blog.springbootdeveloper.service.token;

import iiqcov.blog.springbootdeveloper.auth.jwt.TokenProvider;
import iiqcov.blog.springbootdeveloper.domain.RefreshToken;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new IllegalArgumentException("Unexpected token"));
    }

    public RefreshToken createRefreshToken(User user){
        Duration refreshTokenDuration = Duration.ofDays(28); // 리프레시 토큰의 만료 시간을 설정합니다.
        String refreshTokenValue=tokenProvider.generateToken(user, refreshTokenDuration);

        RefreshToken refreshToken=new RefreshToken(user.getId(), refreshTokenValue);
        return refreshTokenRepository.save(refreshToken);
    }
}
