package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class TaskSettingMapperProvider {
    private static final String TABLE_NAME = "maine_attendance_setting";

    public String addSettingSQLBuilder(Long taskID, byte[] setting) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("task_id", "#{taskID}");
            VALUES("setting", "#{setting}");
        }}.toString();
    }

    public String getSettingSQLBuilder(Long taskID) {
        return new SQL() {{
            SELECT("setting");
            FROM(TABLE_NAME);
            WHERE("task_id = #{taskID}");
        }}.toString();
    }
}
