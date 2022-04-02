package net.dengzixu.maine.mapper.user;

import net.dengzixu.maine.entity.User;
import net.dengzixu.maine.mapper.provider.user.UserMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @InsertProvider(type = UserMapperProvider.class, method = "addSQLBuilder")
    void add(User user);

    /**
     * 通过手机号 获取用户
     * 存在返回 User
     * 不存在返回 null
     *
     * @param phone 手机号
     * @return User
     */
    @SelectProvider(type = UserMapperProvider.class, method = "getByPhoneSQLBuilder")
    User getByPhone(String phone);

    @SelectProvider(type = UserMapperProvider.class, method = "getByIDSQLBuilder")
    User getByID(Long id);

    @SelectProvider(type = UserMapperProvider.class,method = "listUserSQLBuilder")
    List<User> listUser();
}
