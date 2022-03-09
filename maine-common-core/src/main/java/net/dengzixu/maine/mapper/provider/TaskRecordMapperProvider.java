package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class TaskRecordMapperProvider {
    private static final String MAINE_ATTENDANCE_RECORD_TABLE_NAME = "maine_attendance_record";
    private static final String MAINE_USER_TABLE_NAME = "maine_user";

    private static final String[] ALL_COLUMN = {"id", "user_id", "task_id", "create_time", "modify_time"};
    private static final String[] ALL_USER_COLUMN = {"maine_user.id", "maine_user.name",
            "maine_user.password", "maine_user.password_status",
            "maine_user.phone", "maine_user.phone_status",
            "maine_user.email", "maine_user.email_status",
            "maine_user.status",
            "maine_user.create_time", "maine_user.modify_time"};

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
            WHERE("task_id = #{taskID}");
            WHERE("user_id = #{userID}");
        }}.toString();
    }

    public String getUserListByTaskIDSQLBuilder(Long taskID) {
        return new SQL() {{
            SELECT(ALL_USER_COLUMN);
            FROM(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            INNER_JOIN("maine_user ON maine_attendance_record.user_id = maine_user.id");
            WHERE("task_id = #{taskID}");
        }}.toString();
    }
}
