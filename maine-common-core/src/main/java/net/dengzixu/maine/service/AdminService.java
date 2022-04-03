package net.dengzixu.maine.service;

import net.dengzixu.maine.entity.Admin;

public interface AdminService {
    Admin login(String name, String password);

    Admin validate(Long id);
}
