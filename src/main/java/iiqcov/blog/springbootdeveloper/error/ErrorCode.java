package iiqcov.blog.springbootdeveloper.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ENTITY_NOT_FOUND("C03", "Entity Not Found.", HttpStatus.BAD_REQUEST.value()),
    USER_NOT_FOUND("A02", "User is not Found.", HttpStatus.BAD_REQUEST.value());

    private final String code;
    private final String message;
    private int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
