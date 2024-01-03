package iiqcov.blog.springbootdeveloper.api.user;

import iiqcov.blog.springbootdeveloper.domain.RefreshToken;
import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.dto.user.AddUserReqeust;
import iiqcov.blog.springbootdeveloper.dto.user.AddUserResponse;
import iiqcov.blog.springbootdeveloper.dto.user.LoginRequest;
import iiqcov.blog.springbootdeveloper.dto.user.TokenResponse;
import iiqcov.blog.springbootdeveloper.service.token.RefreshTokenService;
import iiqcov.blog.springbootdeveloper.service.token.TokenService;
import iiqcov.blog.springbootdeveloper.service.user.UserService;
import iiqcov.blog.springbootdeveloper.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserApi {
    private final UserService userService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/api/sign-up")
    public ResponseEntity<AddUserResponse> signup(@RequestBody AddUserReqeust request, HttpServletResponse response){
        Optional<User> existingUser = userService.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .build();
        }

        User user=userService.save(request);

        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user);
        String token=tokenService.createNewAccessToken(refreshToken.getRefreshToken());
        CookieUtil.addCookie(response, "token", token, 60*60*2);
        System.out.println("token = " + token);

        AddUserResponse addUserResponse=AddUserResponse.builder()
                .email(user.getEmail())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(addUserResponse);
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response){
        try{
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
            Optional<User> user = userService.findByEmail(username);
            RefreshToken refreshToken=refreshTokenService.findByUserId(user.get().getId());
            String accessToken=tokenService.createNewAccessToken(refreshToken.getRefreshToken());
            CookieUtil.addCookie(response, "token", accessToken, 60*60*2);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }


    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.deleteCookie(request, response, "token");
        request.getSession().invalidate();

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

}
