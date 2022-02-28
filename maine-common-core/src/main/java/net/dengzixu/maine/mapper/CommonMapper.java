package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.SMSCode;
import net.dengzixu.maine.mapper.provider.CommonMapperProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommonMapper {
    @Deprecated
    @InsertProvider(type = CommonMapperProvider.class, method = "saveSMSCodeSQLBuilder")
    void saveSMSCode(String phone, String code);

    @Deprecated
    @SelectProvider(type = CommonMapperProvider.class, method = "getSMSCodeSQLBuilder")
    SMSCode getSMSCode(String phone, String code, boolean allowExpire);

    @Deprecated
    @DeleteProvider(type = CommonMapperProvider.class, method = "deleteSMSCodeSQLBuilder")
    void deleteSMSCode(String phone, String code);
}
