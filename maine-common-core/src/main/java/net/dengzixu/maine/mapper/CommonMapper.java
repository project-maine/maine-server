package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.SMSCode;
import net.dengzixu.maine.mapper.provider.CommonMapperProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommonMapper {
    @InsertProvider(type = CommonMapperProvider.class, method = "saveSMSCodeSQLBuilder")
    void saveSMSCode(String phone, String code);

    @SelectProvider(type = CommonMapperProvider.class, method = "getSMSCodeSQLBuilder")
    SMSCode getSMSCode(String phone, String code, boolean allowExpire);

    @DeleteProvider(type = CommonMapperProvider.class,method = "deleteSMSCodeSQLBuilder")
    void deleteSMSCode(String phone, String code);
}
