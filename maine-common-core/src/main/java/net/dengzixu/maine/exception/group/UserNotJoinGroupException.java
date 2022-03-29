package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class UserNotJoinGroupException extends BusinessException {
    public UserNotJoinGroupException() {
        super("尚未加入此小组", 403);
    }
}
