package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.constant.Constant;
import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.exception.user.PhoneAlreadyUsedException;
import net.dengzixu.maine.exception.common.SMSCodeErrorException;
import net.dengzixu.maine.exception.user.UnknownUserException;
import net.dengzixu.maine.exception.user.UserNotFoundException;
import net.dengzixu.maine.mapper.UserMapper;
import net.dengzixu.maine.service.CommonService;
import net.dengzixu.maine.service.UserService;
import net.dengzixu.maine.utils.PasswordUtils;
import net.dengzixu.maine.utils.RandomGenerator;
import net.dengzixu.maine.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final CommonService commonService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(CommonService commonService, UserMapper userMapper) {
        this.commonService = commonService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
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
    @Transactional
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

    @Override
    @Transactional
    public User loginBySMSCode(String phone, String code) throws SMSCodeErrorException {
        if (!commonService.verifySMS(phone, code)) {
            throw new SMSCodeErrorException();
        }

        // 判断用户是否存在 如果存在就返回 User，不存在就创建用户
        User user = userMapper.getByPhone(phone);

        // 存在就返回 User
        if (null != user) {
            return user;
        }

        // 不存在就创建用户
        registerByPhone("用户 " + phone, RandomGenerator.nextPassword(), phone);

        // 再获取一次
        user = userMapper.getByPhone(phone);

        return user;
    }

    @Override
    public User getUserByID(Long id) {
        User user = userMapper.getByID(id);

        if (null == user) {
            throw new UnknownUserException();
        }

        return user;
    }
}
