package net.dengzixu.maine.service.impl;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.mapper.UserMapper;
import net.dengzixu.maine.service.UserService;
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
    public User login() {
        return userMapper.getByPhone("13998109207");
    }
}
