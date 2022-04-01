package net.dengzixu.maine.service.impl;

import net.dengzixu.constant.Constant;
import net.dengzixu.maine.entity.Admin;
import net.dengzixu.maine.exception.user.UserNotFoundException;
import net.dengzixu.maine.mapper.admin.AdminMapper;
import net.dengzixu.maine.service.AdminService;
import net.dengzixu.maine.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {


    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin login(String name, String password) {
        String encryptPassword = PasswordUtils.encrypt(password, Constant.ADMIN_PASSWORD_SALT);

        Admin admin = adminMapper.get(name, encryptPassword);

        if (null == admin) {
            throw new UserNotFoundException();
        }

        return admin;
    }
}
