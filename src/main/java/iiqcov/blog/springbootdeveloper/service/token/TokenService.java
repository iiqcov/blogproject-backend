package iiqcov.blog.springbootdeveloper.service.token;

import iiqcov.blog.springbootdeveloper.auth.jwt.TokenProvider;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

        return tokenProvider.generateToken(user, Duration.ofDays(1));
    }

    public boolean isMatched(HttpServletRequest request){
        return tokenProvider.validToken(getTokenFromCookie(request));
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
