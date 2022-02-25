package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class AttendanceMapperProvider {

    private static final String MAINE_ATTENDANCE_TASK_TABLE_NAME = "maine_attendance_task";

    public String addTaskSQLBuilder(String title, String description, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            VALUES("title", "#{title}");
            VALUES("description", "#{description}");
            VALUES("user_id", "#{userID}");
        }}.toString();
    }
}
