package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.mapper.provider.UserMapperProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     * 通过手机号 获取用户
     * 存在返回 User
     * 不存在返回 null
     *
     * @param phone 手机号
     * @return User
     */

    @SelectProvider(type = UserMapperProvider.class, method = "UserMapperSQLBuilder")
    User getByPhone(String phone);
}
