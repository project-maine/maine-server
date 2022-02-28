package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class TaskRecordMapperProvider {
    private static final String MAINE_ATTENDANCE_RECORD_TABLE_NAME = "maine_attendance_record";

    private static final String[] ALL_COLUMN = {"id", "user_id", "task_id", "create_time", "modify_time"};

    public String addRecordSQLBuilder(Long taskID, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            VALUES("user_id", "#{userID}");
            VALUES("task_id", "#{taskID}");
        }}.toString();
    }

    public String getAttendanceTaskByIDAndUserIDSQLBuilder(Long taskID, Long userID) {
        return new SQL() {{
            SELECT(ALL_COLUMN);
            FROM(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            WHERE("task_id", "#{taskID}");
            WHERE("user_id", "#{userID}");
        }}.toString();
    }
}
