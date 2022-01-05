package net.dengzixu.maine.exception.common;

import net.dengzixu.maine.exception.BusinessException;

public class SMSCodeSendFrequentlyException extends BusinessException {
    public SMSCodeSendFrequentlyException() {
        super("验证码发送过于频繁", 403);
    }
}
