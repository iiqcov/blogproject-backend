package iiqcov.blog.springbootdeveloper.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddUserResponse {
    private String nickname;
    private String email;
}
