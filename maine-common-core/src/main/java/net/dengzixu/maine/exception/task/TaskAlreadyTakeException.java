package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class TaskAlreadyTakeException extends BusinessException {
    public TaskAlreadyTakeException() {
        super("已经参与过了", 403);
    }
}
