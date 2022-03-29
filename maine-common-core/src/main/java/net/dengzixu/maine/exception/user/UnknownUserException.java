package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class UnknownUserException extends BusinessException {
    public UnknownUserException() {
        super("用户不存在", 404);
    }
}
