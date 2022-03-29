package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class TaskCodeErrorException extends BusinessException {
    public TaskCodeErrorException() {
        super("考勤码错误或已过期", 404);
    }
}
