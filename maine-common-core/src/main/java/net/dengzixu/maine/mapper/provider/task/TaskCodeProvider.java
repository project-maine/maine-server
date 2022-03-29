package net.dengzixu.maine.mapper.provider.task;

import org.apache.ibatis.jdbc.SQL;

public class TaskCodeProvider {
    private static final String TABLE_NAME = "maine_attendance_task_code";

    private static final String[] ALL_COLUMNS = new String[]{"task_id", "code", "expire_time", "create_time", "modify_time"};

    public String getByCodeSQLBuilder(String code, Boolean allowExpire) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("code = #{code}");
            if (allowExpire) {
                WHERE("expire_time < now()");
            }
        }}.toString();
    }

    public String getByTaskIdSQLBuilder(Long taskID, Boolean allowExpire) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("task_id = #{taskID}");
            if (allowExpire) {
                WHERE("expire_time < now()");
            }
        }}.toString();
    }

    public String addSQLBuilder(Long taskID, String code, Integer ttl) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("task_id", "#{taskID}");
            VALUES("code", "#{code}");
            VALUES("expire_time", "DATE_ADD(now(), INTERVAL " + ttl + " SECOND)");
        }}.toString();
    }
}
