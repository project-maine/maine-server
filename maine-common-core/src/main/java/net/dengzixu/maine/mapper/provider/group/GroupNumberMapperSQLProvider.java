package net.dengzixu.maine.mapper.provider.group;

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

    public String modifyStatusSQLBuilder(Long groupID, Long userID, Integer Status) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("status = #{Status}");
            WHERE("group_id = #{groupID}");
            WHERE("user_id = #{userID}");
        }}.toString();
    }

    public String isUserInGroup(Long groupID, Long userID, Boolean allowLeave) {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM(TABLE_NAME);
            WHERE("group_id = #{groupID}");
            WHERE("user_id = #{userID}");
            if (!allowLeave) {
                WHERE("status = 0");
            }
            LIMIT(1);
        }}.toString();
    }

    public String getGroupNumberListSQLBuilder(Long groupID, Long userID) {
        return new SQL() {{
            SELECT("U.id as user_id, u.name as user_name,GN.create_Time as create_time");
            FROM("maine_group_number AS GN");
            INNER_JOIN("maine_user AS U ON GN.user_id = U.id");
            WHERE("GN.group_id = #{groupID}");
            AND();
            WHERE("GN.user_id = #{userID}");
        }}.toString();
    }

    public String getJoinedGroupListSQLBuilder(Long userID) {
        return new SQL() {{
            SELECT("G.id AS group_id",
                    "G.name AS group_name",
                    "G.description AS group_description",
                    "G.Status AS group_status",
                    "GN.create_time AS join_time");
            FROM("maine_group_number AS GN");
            INNER_JOIN("maine_group AS G ON GN.group_id = G.id");
            WHERE("GN.user_id = #{userID}");
        }}.toString();
    }
}
