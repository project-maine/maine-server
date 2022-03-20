package net.dengzixu.maine.mapper.provider.group;

import org.apache.ibatis.jdbc.SQL;

public class GroupMapperProvider {
    private static final String TABLE_NAME = "maine_group";

    private static final String[] ALL_COLUMNS = {
            "id", "name", "description", "user_id", "status", "create_time", "modify_time"
    };

    public String addSQLBuilder(Long id, String name, String description, Long userID) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("id", "#{id}");
            VALUES("user_id", "#{userID}");
            VALUES("name", "#{name}");
            VALUES("description", "#{description}");
            VALUES("status", "0");
        }}.toString();
    }

    public String getByIDSQLBuilder(Long groupID) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("id = #{groupID}");
        }}.toString();
    }

    public String getListByUserIDSQLBuilder(Long userID) {
        return new SQL() {{
            SELECT(ALL_COLUMNS);
            FROM(TABLE_NAME);
            WHERE("user_id = #{userID}");
        }}.toString();
    }

    public String modifyGroupStatusSQLBuilder(Long groupID) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("status = #{status}");
            WHERE("id = #{groupID}");
        }}.toString();
    }
}
