package net.dengzixu.maine.mapper.admin;

import net.dengzixu.maine.entity.Admin;
import net.dengzixu.maine.mapper.provider.admin.AdminMapperProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    @SelectProvider(value = AdminMapperProvider.class, method = "loginSQLBuilder")
    Admin login(String name, String password);

    @SelectProvider(value = AdminMapperProvider.class, method = "getByIDSQLBuilder")
    Admin getByID(Long id);
}
