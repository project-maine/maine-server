package net.dengzixu.maine.exception.common;

import net.dengzixu.maine.exception.BusinessException;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException() {
        super("用户身份过期，请重新登录", 403);
    }
}
