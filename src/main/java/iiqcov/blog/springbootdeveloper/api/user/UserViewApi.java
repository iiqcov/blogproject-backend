package iiqcov.blog.springbootdeveloper.api.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserViewApi {
    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/sign-up")
    public void signup(){

    }
}
