package net.dengzixu.maine.mapper.provider.group;

import org.apache.ibatis.jdbc.SQL;

public class CommonMapperProvider {
    private static final String MAINE_SMS_CODE_TABLE_NAME = "maine_sms_code";

    public String saveSMSCodeSQLBuilder(String phone, String code) {
        return new SQL() {{
            INSERT_INTO(MAINE_SMS_CODE_TABLE_NAME);
            VALUES("phone", "#{phone}");
            VALUES("code", "#{code}");
            VALUES("expire_time", "DATE_ADD(now(), INTERVAL 5 MINUTE)");
        }}.toString();
    }

    public String getSMSCodeSQLBuilder(String phone, String code, boolean allowExpire) {
        String[] columns = {"trace_id", "phone", "code", "expire_time", "create_time"};
        return new SQL() {{
            SELECT(columns);
            FROM(MAINE_SMS_CODE_TABLE_NAME);
            WHERE("phone", "#{phone}");
            WHERE("code", "#{code}");
            if (!allowExpire) {
                WHERE("expire_time >= now()");
            }
            LIMIT(1);
        }}.toString();
    }

    public String deleteSMSCodeSQLBuilder(String phone, String code) {
        return new SQL() {{
            DELETE_FROM(MAINE_SMS_CODE_TABLE_NAME);
            WHERE("phone", "#{phone}");
            WHERE("code", "#{code}");
        }}.toString();

    }
}
