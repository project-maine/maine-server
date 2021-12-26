package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.exception.user.PhoneAlreadyUsedException;
import net.dengzixu.maine.exception.user.UserNotFoundException;

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
}
