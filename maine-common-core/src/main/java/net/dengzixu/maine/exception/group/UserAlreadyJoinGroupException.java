package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class UserAlreadyJoinGroupException extends BusinessException {
    public UserAlreadyJoinGroupException() {
        super("已加入此小组", 403);
    }
}
