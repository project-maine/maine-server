package net.dengzixu.maine.exception.task;

import net.dengzixu.maine.exception.BusinessException;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException() {
        super("无效的 Token", 403);
    }
}
