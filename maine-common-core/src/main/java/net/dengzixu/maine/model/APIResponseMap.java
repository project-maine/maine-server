package net.dengzixu.maine.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

import java.util.LinkedHashMap;

public final class APIResponseMap extends LinkedHashMap<String, Object> {
    public APIResponseMap(Status status, Integer code, String message, String token, Object data) {
        this.put("status", status);
        this.put("code", code);
        this.put("message", message);

        if (StringUtils.isNotBlank(token)) {
            this.put("token", token);
        }

        if (null != data) {
            put("data", data);
        }
    }

    public APIResponseMap(Status status, Integer code, String message, String token) {
        this(status, code, message, token, null);
    }

    public APIResponseMap(Status status, Integer code, String message, Object data) {
        this(status, code, message, null, data);
    }

    public APIResponseMap(Status status, Integer code, String message) {
        this(status, code, message, null, null);
    }

    public static APIResponseMap SUCCEEDED(String message, String token, Object data) {
        return new APIResponseMap(Status.SUCCEEDED, 0, message, token, data);
    }

    public static APIResponseMap SUCCEEDED(String message, String token) {
        return new APIResponseMap(Status.SUCCEEDED, 0, message, token, null);
    }

    public static APIResponseMap SUCCEEDED(String message, Object data) {
        return new APIResponseMap(Status.SUCCEEDED, 0, message, null, data);
    }

    public static APIResponseMap SUCCEEDED(String message) {
        return new APIResponseMap(Status.SUCCEEDED, 0, message, null, null);
    }

    public static APIResponseMap FAILED(Integer code, String message, Object data) {
        return new APIResponseMap(Status.FAILED, code, message, data);
    }

    public static APIResponseMap FAILED(Integer code, String message) {
        return new APIResponseMap(Status.FAILED, code, message, null);
    }

    public static APIResponseMap FAILED(Integer code, FieldError fieldError) {
        return new APIResponseMap(Status.FAILED, code, null != fieldError ? fieldError.getDefaultMessage() : "数据错误", null, null);
    }

    public enum Status {
        SUCCEEDED,
        FAILED
    }
}
