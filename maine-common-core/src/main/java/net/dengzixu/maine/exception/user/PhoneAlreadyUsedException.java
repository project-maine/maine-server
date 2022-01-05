package net.dengzixu.maine.exception.user;

import net.dengzixu.maine.exception.BusinessException;

public class PhoneAlreadyUsedException extends BusinessException {
    public PhoneAlreadyUsedException() {
        super("此手机号已被使用", 403);
    }
}
