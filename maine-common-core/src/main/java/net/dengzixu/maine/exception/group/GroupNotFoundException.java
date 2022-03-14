package net.dengzixu.maine.exception.group;

import net.dengzixu.maine.exception.BusinessException;

public class GroupNotFoundException extends BusinessException {
    public GroupNotFoundException() {
        super("小组不存在", 404);
    }
}
