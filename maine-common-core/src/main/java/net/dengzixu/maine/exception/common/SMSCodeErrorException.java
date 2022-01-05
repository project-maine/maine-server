package net.dengzixu.maine.exception.common;

import net.dengzixu.maine.exception.BusinessException;

public class SMSCodeErrorException extends BusinessException {
    public String getMessage() {
        return super.getMessage();
    }

    public SMSCodeErrorException() {
        super("验证码错误", 403);
    }
}
