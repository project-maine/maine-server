package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class SMSCodeErrorException extends BusinessException {
    private static final String message = "验证码错误";

    public String getMessage() {
        return message;
    }
}
