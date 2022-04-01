package net.dengzixu.maine.mapper.provider.admin;

import org.apache.ibatis.jdbc.SQL;

public class AdminMapperProvider {
    private static final String MAINE_ADMIN_TABLE_NAME = "maine_admin";

    private static final String[] ALL_COLUMNS = new String[]{
            "id", "name", "password", "create_time", "modify_time"
    };

    public String getSQLBuilder(String name, String password) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(MAINE_ADMIN_TABLE_NAME);
            WHERE("name = #{name}");
            AND();
            WHERE("password = #{password}");
        }}.toString();
    }
}
