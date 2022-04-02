package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.exception.common.SMSCodeErrorException;
import net.dengzixu.maine.exception.user.PhoneAlreadyUsedException;
import net.dengzixu.maine.exception.user.UserNotFoundException;

import java.util.List;

public interface UserService {
    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    手机号
     */
    void registerByPhone(String username, String password, String phone) throws PhoneAlreadyUsedException;

    /**
     * 登录-手机号密码
     *
     * @param phone    手机号
     * @param password 密码
     * @return User
     */
    User loginByPhoneAndPassword(String phone, String password) throws UserNotFoundException;

    /**
     * 登录 验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return User
     * @throws SMSCodeErrorException 验证码错误
     */
    User loginBySMSCode(String phone, String code) throws SMSCodeErrorException;

    /**
     * 通过 用户ID 获取用户
     *
     * @param id 用户ID
     * @return User
     */
    User getUserByID(Long id);

    /**
     * 获取所有用户
     *
     * @return List<User>
     */
    List<User> getAllUser();

    /**
     * 验证用户是否有效
     *
     * @param id 用户ID
     * @return 有效返回 true, 无效返回 false
     */
    Boolean validate(Long id);
}
