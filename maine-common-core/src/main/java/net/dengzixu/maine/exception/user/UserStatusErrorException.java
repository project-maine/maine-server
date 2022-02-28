package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class UserStatusErrorException extends BusinessException {
    public UserStatusErrorException() {
        super("用户状态不正确", 403);
    }
}
