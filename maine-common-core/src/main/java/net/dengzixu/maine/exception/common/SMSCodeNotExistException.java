package net.dengzixu.maine.exception.common;

import net.dengzixu.maine.exception.BusinessException;

@Deprecated
public class SMSCodeNotExistException extends BusinessException {
    public SMSCodeNotExistException() {
        super("验证码不存在", 404);
    }
}
