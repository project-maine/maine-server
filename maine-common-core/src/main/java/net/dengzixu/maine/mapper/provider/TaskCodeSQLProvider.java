package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class TaskCodeSQLProvider {
    private static final String TABLE_NAME = "maine_attendance_task_code";

    private static final String[] ALL_COLUMNS = new String[]{"task_id", "code", "expire_time", "create_time", "modify_time"};

    public String getSQLBuilder(String code, Boolean allowExpire) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("code = #{code}");
            if (allowExpire) {
                WHERE("expire_time < now()");
            }
        }}.toString();
    }
}
