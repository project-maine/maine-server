package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class GroupLimitException extends BusinessException {
    public GroupLimitException() {
        super("用户不在允许小组之内", 403);
    }
}
