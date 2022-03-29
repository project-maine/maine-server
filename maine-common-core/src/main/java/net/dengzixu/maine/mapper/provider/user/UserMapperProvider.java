package net.dengzixu.maine.mapper.provider.user;

import net.dengzixu.maine.entity.User;
import org.apache.ibatis.jdbc.SQL;

public class UserMapperProvider {
    private static final String MAINE_USER_TABLE_NAME = "maine_user";

    private static final String[] ALL_COLUMN = {"id", "name",
            "password", "password_status",
            "phone", "phone_status",
            "email", "email_status",
            "status",
            "create_Time", "modify_time"};

    public String addSQLBuilder(User user) {
        return new SQL() {{
            INSERT_INTO(MAINE_USER_TABLE_NAME);
            VALUES("id", "#{id}");
            VALUES("name", "#{name}");
            VALUES("password", "#{password}");
            VALUES("password_status", "#{passwordStatus}");
            VALUES("phone", "#{phone}");
            VALUES("phone_status", "#{phoneStatus}");
            VALUES("email", "#{email}");
            VALUES("email_status", "#{emailStatus}");
            VALUES("status", "#{status}");
        }}.toString();
    }

    public String getByPhoneSQLBuilder(String phone) {
        return new SQL() {{
            SELECT(ALL_COLUMN);
            FROM(MAINE_USER_TABLE_NAME);
            WHERE("phone = #{phone}");
        }}.toString();
    }

    public String getByIDSQLBuilder(Long id) {
        return new SQL() {{
            SELECT(ALL_COLUMN);
            FROM(MAINE_USER_TABLE_NAME);
            WHERE("id = #{id}");
        }}.toString();
    }
}
