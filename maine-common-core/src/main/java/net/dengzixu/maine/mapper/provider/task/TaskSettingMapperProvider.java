package net.dengzixu.maine.mapper.provider.task;

import org.apache.ibatis.jdbc.SQL;

public class TaskSettingMapperProvider {
    private static final String TABLE_NAME = "maine_attendance_setting";

    private static final String[] ALL_COLUMNS = {"task_id", "setting", "create_time", "modify_time"};

    public String addSettingSQLBuilder(Long taskID, byte[] setting) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("task_id", "#{taskID}");
            VALUES("setting", "#{setting}");
        }}.toString();
    }

    public String getSettingSQLBuilder(Long taskID) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("task_id = #{taskID}");
        }}.toString();
    }
}
