package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super("用户不存在或密码错误", 404);
    }
}
