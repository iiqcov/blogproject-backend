package iiqcov.blog.springbootdeveloper.service.token;

import iiqcov.blog.springbootdeveloper.config.jwt.TokenProvider;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        if (!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId=refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user=userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
