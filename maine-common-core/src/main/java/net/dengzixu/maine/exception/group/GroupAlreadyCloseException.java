package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class GroupAlreadyCloseException extends BusinessException {
    public GroupAlreadyCloseException() {
        super("小组已经关闭", 403);
    }
}
