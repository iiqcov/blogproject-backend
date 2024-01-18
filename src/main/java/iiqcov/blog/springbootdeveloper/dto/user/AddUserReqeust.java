package iiqcov.blog.springbootdeveloper.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserReqeust {
    private String email;
    private String password;
    private String nickname;
}
