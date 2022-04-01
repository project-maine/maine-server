package net.dengzixu.maine;

import net.dengzixu.constant.Constant;
import net.dengzixu.maine.utils.PasswordUtils;
import org.junit.jupiter.api.Test;


public class GenerateAdminPassword {
    @Test
    public void genPassword() {
        String password = "dengzixu";
        System.out.println(PasswordUtils.encrypt(password, Constant.ADMIN_PASSWORD_SALT));
    }
}
