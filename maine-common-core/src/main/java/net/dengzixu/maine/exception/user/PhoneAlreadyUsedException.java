package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class PhoneAlreadyUsedException extends BusinessException {
    private static final String message = "此手机号已使用。";

    public String getMessage() {
        return message;
    }
}
