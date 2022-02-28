package net.dengzixu.maine.exception.attendance;

import net.dengzixu.maine.exception.BusinessException;

public class AttendanceAlreadyTakeException extends BusinessException {
    public AttendanceAlreadyTakeException() {
        super("已经参与过了", 403);
    }
}
