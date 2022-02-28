package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class AttendanceMapperProvider {

    private static final String MAINE_ATTENDANCE_TASK_TABLE_NAME = "maine_attendance_task";
    private static final String MAINE_ATTENDANCE_RECORD_TABLE_NAME = "maine_attendance_record";

    public String addTaskSQLBuilder(String title, String description, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_TASK_TABLE_NAME);
            VALUES("title", "#{title}");
            VALUES("description", "#{description}");
            VALUES("user_id", "#{userID}");
        }}.toString();
    }

    public String addRecordSQLBuilder(Long taskID, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            VALUES("user_id", "#{userID}");
            VALUES("task_id", "#{taskID}");
        }}.toString();
    }
}
