package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class TaskNotFoundException extends BusinessException {
    public TaskNotFoundException() {
        super("找不到对应的任务", 404);
    }
}
