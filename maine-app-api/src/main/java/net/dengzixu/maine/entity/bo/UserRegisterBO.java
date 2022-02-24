package net.dengzixu.maine.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRegisterBO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 12, message = "用户名长度不能小于2个字符或大于12个字符")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于六位")
    private String password;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4(?:(?:10|4[01])\\d{3}|[68]\\d{4}|[579]\\d{2}))\\d{6}$", message = "手机号格式不正确")
    private String phone;

//    @NotBlank(message = "邮箱不能为空")
//    @Email(message = "邮箱格式不正确")
//    private String email;
}
