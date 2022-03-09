package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class TaskMapperProvider {
    private static final String MAINE_ATTENDANCE_TASK_TABLE_NAME = "maine_attendance_task";

    private static final String[] ALL_COLUMNS = new String[]{"id", "title", "description", "user_id", "status", "create_time", "modify_time"};

    public String addTaskSQLBuilder(String title, String description, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            VALUES("title", "#{title}");
            VALUES("description", "#{description}");
            VALUES("user_id", "#{userID}");
        }}.toString();
    }

    public String getTaskSQLBuilder(Long taskID) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            WHERE("id = #{taskID}");
        }}.toString();
    }

    public String getTaskListByUserIDSQLBuilder(Long userID) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            WHERE("user_id = #{userID}");
        }}.toString();
    }

    public String modifyTaskStatusSQLBuilder(Long taskID, Integer status) {
        return new SQL() {{
            UPDATE(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            SET("status = #{status}");
            WHERE("id = #{taskID}");
        }}.toString();
    }
}
