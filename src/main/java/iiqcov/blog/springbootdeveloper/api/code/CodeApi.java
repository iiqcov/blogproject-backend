package iiqcov.blog.springbootdeveloper.api.code;

import iiqcov.blog.springbootdeveloper.domain.RefreshToken;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.service.code.CodeService;
import iiqcov.blog.springbootdeveloper.service.token.RefreshTokenService;
import iiqcov.blog.springbootdeveloper.service.token.TokenService;
import iiqcov.blog.springbootdeveloper.service.user.UserService;
import iiqcov.blog.springbootdeveloper.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodeApi {
    private final CodeService codeService;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/qcov/{url}")
    public ResponseEntity<Void> getToken(@PathVariable String url, HttpServletRequest request, HttpServletResponse response){
        if (codeService.urlMatch(url)){
            User user=userService.save(User.builder()
                    .ipAddress(request.getRemoteAddr())
                    .build());

            RefreshToken refreshToken=refreshTokenService.createRefreshToken(user);
            String token=tokenService.createNewAccessToken(refreshToken.getRefreshToken());
            System.out.println("token = " + token);
            CookieUtil.addCookie(response, "token", token, 60*60*2);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.deleteCookie(request, response, "token");
        request.getSession().invalidate();

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
