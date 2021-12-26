package net.dengzixu.maine.mapper.provider;

import net.dengzixu.maine.entity.User;
import org.apache.ibatis.jdbc.SQL;

public class UserMapperProvider {
    private static final String MAINE_USER_TABLE_NAME = "maine_user";

    private static final String[] ALL_COLUMN = {"trace_id", "id", "name",
            "password", "phone", "email",
            "status", "create_Time", "modify_time"};

    public String addSQLBuilder(User user) {
        return new SQL() {{
            INSERT_INTO(MAINE_USER_TABLE_NAME);
            VALUES("id", "#{id}");
            VALUES("name", "#{name}");
            VALUES("password", "#{password}");
            VALUES("phone", "#{phone}");
            VALUES("email", "#{email}");
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
}
