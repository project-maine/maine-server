package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class UserAlreadyJoinException extends BusinessException {
    public UserAlreadyJoinException() {
        super("已加入此小组", 403);
    }
}
