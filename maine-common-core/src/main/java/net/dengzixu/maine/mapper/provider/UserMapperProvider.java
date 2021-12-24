package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserMapperProvider {
    private static final String MAINE_USER_TABLE_NAME = "maine_user";

    private static final String[] ALL_COLUMN = {"trace_id", "id", "name",
            "password", "phone", "email",
            "status", "create_Time", "modify_time"};

    public String UserMapperSQLBuilder(String phone) {
        return new SQL() {{
            SELECT(ALL_COLUMN);
            FROM(MAINE_USER_TABLE_NAME);
            WHERE("phone = #{phone}");
        }}.toString();
    }
}
