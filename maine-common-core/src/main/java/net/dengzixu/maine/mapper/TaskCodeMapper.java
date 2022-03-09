package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.TaskCode;
import net.dengzixu.maine.mapper.provider.TaskCodeSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskCodeMapper {
    @SelectProvider(type = TaskCodeSQLProvider.class, method = "getByCodeSQLBuilder")
    TaskCode getByCode(String code, Boolean allowExpire);

    @SelectProvider(type = TaskCodeSQLProvider.class, method = "getByTaskIdSQLBuilder")
    TaskCode getByTaskID(Long taskID, Boolean allowExpire);

    @InsertProvider(type = TaskCodeSQLProvider.class, method = "addSQLBuilder")
    void add(Long taskID, String code, Integer ttl);
}
