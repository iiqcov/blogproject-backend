package iiqcov.blog.springbootdeveloper.service.token;

import iiqcov.blog.springbootdeveloper.config.jwt.TokenProvider;
import iiqcov.blog.springbootdeveloper.domain.RefreshToken;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.error.exception.UserNotFoundException;
import iiqcov.blog.springbootdeveloper.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new IllegalArgumentException("Unexpected token"));
    }

    public RefreshToken findByUserId(Long userId){
        Optional<RefreshToken> refreshToken=refreshTokenRepository.findByUserId(userId);
        if (!refreshToken.isPresent()){
            throw new UserNotFoundException();
        }
        return refreshToken.get();
    }

    public RefreshToken createRefreshToken(User user){
        Duration refreshTokenDuration = Duration.ofDays(7); // 리프레시 토큰의 만료 시간을 설정합니다.
        String refreshTokenValue=tokenProvider.generateToken(user, refreshTokenDuration);

        RefreshToken refreshToken=new RefreshToken(user.getId(), refreshTokenValue);
        return refreshTokenRepository.save(refreshToken);
    }
}
