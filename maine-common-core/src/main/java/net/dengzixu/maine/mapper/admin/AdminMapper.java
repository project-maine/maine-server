package net.dengzixu.maine.mapper.admin;

import net.dengzixu.maine.entity.Admin;
import net.dengzixu.maine.mapper.provider.admin.AdminMapperProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    @SelectProvider(value = AdminMapperProvider.class, method = "getSQLBuilder")
    Admin get(String name, String password);
}
