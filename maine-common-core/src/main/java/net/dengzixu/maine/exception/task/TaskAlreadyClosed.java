package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class TaskAlreadyClosed extends BusinessException {
    public TaskAlreadyClosed() {
        super("任务已关闭", 403);
    }
}
