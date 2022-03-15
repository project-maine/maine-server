package net.dengzixu.maine.mapper.task;

import net.dengzixu.maine.entity.TaskCode;
import net.dengzixu.maine.mapper.provider.task.TaskCodeProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskCodeMapper {
    @SelectProvider(type = TaskCodeProvider.class, method = "getByCodeSQLBuilder")
    TaskCode getByCode(String code, Boolean allowExpire);

    @SelectProvider(type = TaskCodeProvider.class, method = "getByTaskIdSQLBuilder")
    TaskCode getByTaskID(Long taskID, Boolean allowExpire);

    @InsertProvider(type = TaskCodeProvider.class, method = "addSQLBuilder")
    void add(Long taskID, String code, Integer ttl);
}
