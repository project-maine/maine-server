package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.TaskCode;
import net.dengzixu.maine.mapper.provider.TaskCodeSQLProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskCodeMapper {
    @SelectProvider(type = TaskCodeSQLProvider.class, method = "getSQLBuilder")
    TaskCode get(String code, Boolean allowExpire);
}
