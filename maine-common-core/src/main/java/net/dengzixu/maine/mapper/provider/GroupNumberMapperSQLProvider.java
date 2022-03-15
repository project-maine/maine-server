package net.dengzixu.maine.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class GroupNumberMapperSQLProvider {
    private final static String TABLE_NAME = "maine_group_number";
    private final static String[] ALL_COLUMNS = {"user_id", "group_id", "status", "create_time", "modify_time"};

    public String addSQLBuilder(Long groupID, Long userID) {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            VALUES("user_id", "#{userID}");
            VALUES("group_id", "#{groupID}");
            VALUES("status", "0");
        }}.toString();
    }

    public String isUserInGroup(Long groupID, Long userID) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM(TABLE_NAME);
            WHERE("group_id = #{groupID}");
            WHERE("user_id = #{userID}");
            LIMIT(1);
        }}.toString();
    }
}
