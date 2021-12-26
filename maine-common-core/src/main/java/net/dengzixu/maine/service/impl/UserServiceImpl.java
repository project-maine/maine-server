package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.constant.Constant;
import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.exception.user.PhoneAlreadyUsedException;
import net.dengzixu.maine.exception.user.UserNotFoundException;
import net.dengzixu.maine.mapper.UserMapper;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.PasswordUtils;
import net.dengzixu.maine.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void registerByPhone(String username, String password, String phone) throws PhoneAlreadyUsedException {
        // 判断手机号是否已经被注册
        if (null != userMapper.getByPhone(phone)) {
            throw new PhoneAlreadyUsedException();
        }

        // 密码加密
        String encryptedPassword = PasswordUtils.encrypt(password, Constant.PASSWORD_SALT);

        // 构造 User 对象
        User user = new User();
        user.setId(new SnowFlake(0, 0).nextId());
        user.setName(username);
        user.setPassword(encryptedPassword);
        user.setPhone(phone);
        user.setEmail("");
        user.setStatus(0);

        // 写入数据库
        userMapper.add(user);
    }

    @Override
    public User loginByPhoneAndPassword(String phone, String password) throws UserNotFoundException {
        // 密码加密
        String encryptedPassword = PasswordUtils.encrypt(password, Constant.PASSWORD_SALT);

        // 查询用户
        User user = userMapper.getByPhone(phone);
        if (null == user || !encryptedPassword.equals(user.getPassword())) {
            throw new UserNotFoundException();
        }

        return user;
    }
}
