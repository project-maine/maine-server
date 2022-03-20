package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class GroupAlreadyOpenException extends BusinessException {
    public GroupAlreadyOpenException() {
        super("小组已经启用", 403);
    }
}
