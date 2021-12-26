package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    private static final String message = "用户不存在或密码错误";

    public String getMessage() {
        return message;
    }
}
