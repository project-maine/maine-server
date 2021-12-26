package net.dengzixu.maine.exception;

public class BusinessException extends RuntimeException {
    private static final String message = "业务异常";

    public String getMessage() {
        return message;
    }
}
