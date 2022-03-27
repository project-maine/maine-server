package net.dengzixu.maine.mapper.provider.task;

import org.apache.ibatis.jdbc.SQL;

public class TaskRecordMapperProvider {
    private static final String MAINE_ATTENDANCE_RECORD_TABLE_NAME = "maine_attendance_record";

    private static final String[] ALL_COLUMN = {"id", "user_id", "task_id", "status", "create_time", "modify_time"};

    public String addRecordSQLBuilder(Long taskID, Long userID) {
        return new SQL() {{
            INSERT_INTO(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            VALUES("user_id", "#{userID}");
            VALUES("task_id", "#{taskID}");
            VALUES("status", "0");
        }}.toString();
    }

    public String getRecordByTaskIDAndUserIDSQLBuilder(Long taskID, Long userID) {
        return new SQL() {{
            SELECT(ALL_COLUMN);
            FROM(MAINE_ATTENDANCE_RECORD_TABLE_NAME);
            WHERE("task_id = #{taskID}");
            WHERE("user_id = #{userID}");
        }}.toString();
    }

    public String getParticipantListByTaskIDSQLBuilder(Long taskID) {
        return new SQL() {{
            SELECT("U.id AS user_id", "U.name AS user_name", "TR.create_time AS take_Time");
            FROM("maine_attendance_record AS TR");
            INNER_JOIN("maine_user AS U ON TR.user_id = U.id");
            WHERE("task_id = #{taskID}");
        }}.toString();
    }

    public String listTakeRecordSQLBuilder(Long userID) {
        return new SQL() {{
            SELECT("R.task_id AS task_id", "R.status AS record_status", "R.create_time AS record_create_time",
                    "T.title AS task_title", "T.description AS task_description", "T.status AS task_status");
            FROM("maine_attendance_record AS R");
            INNER_JOIN("maine_attendance_task AS T ON R.task_id = T.id");
            WHERE("R.user_id = #{userid}");
        }}.toString();
    }
}
