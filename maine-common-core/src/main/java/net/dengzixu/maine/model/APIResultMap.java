package net.dengzixu.maine.model;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class APIResultMap {
    private String status = "error";
    private Integer code = 0;
    private String message = null;
    private String token = "";
    private Object data = null;

    private APIResultMap() {
    }

    public APIResultMap(String status, Integer code, String message, String token, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    private APIResultMap(String status, Integer code, String message) {
        this(status, code, message, "", null);
    }

    public static APIResultMap Builder() {
        return new APIResultMap();
    }

    public static Map<String, Object> SUCCESS(String message) {
        return new APIResultMap("success", 0, message).Build();
    }

    public static Map<String, Object> SUCCESS(String message, String token) {
        return new APIResultMap("success", 0, message, token, null).Build();
    }

    public static Map<String, Object> SUCCESS(String message, Object data) {
        return new APIResultMap("success", 0, message, "", data).Build();
    }

    public static Map<String, Object> SUCCESS(String message, String token, Object data) {
        return new APIResultMap("success", 0, message, token, data).Build();
    }

    public static Map<String, Object> ERROR(Integer code, String message) {
        return new APIResultMap("error", code, message, "", null).Build();
    }

    public static Map<String, Object> ERROR(Integer code, String message, Object data) {
        return new APIResultMap("error", code, message, "", data).Build();
    }

    public APIResultMap STATUS(Boolean isSuccess) {
        this.status = isSuccess ? "success" : "error";

        return this;
    }

    public APIResultMap CODE(Integer code) {
        this.code = code;

        return this;
    }

    public APIResultMap MESSAGE(String message) {
        this.message = message;

        return this;
    }

    public APIResultMap TOKEN(String token) {
        this.token = token;

        return this;
    }

    public APIResultMap DATA(Object data) {
        this.data = data;

        return this;
    }

    public Map<String, Object> Build() {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        resultMap.put("status", status);
        resultMap.put("code", code);
        resultMap.put("message", message);
        if (StringUtils.isNotBlank(token)) {
            resultMap.put("token", token);
        }
        if (null != data) {
            resultMap.put("data", data);
        }

        return resultMap;
    }
}
