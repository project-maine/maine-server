package net.dengzixu.maine.exception;

public class BusinessException extends RuntimeException {
    protected int httpStatusCode;

    public String getMessage() {
        return super.getMessage();
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public BusinessException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
