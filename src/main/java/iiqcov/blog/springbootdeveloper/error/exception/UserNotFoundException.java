package iiqcov.blog.springbootdeveloper.error.exception;

import iiqcov.blog.springbootdeveloper.error.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
