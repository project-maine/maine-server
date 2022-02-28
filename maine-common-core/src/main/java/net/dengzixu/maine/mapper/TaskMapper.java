package net.dengzixu.maine.mapper;

import net.dengzixu.maine.mapper.provider.TaskMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskMapper {
    @InsertProvider(type = TaskMapperProvider.class, method = "addTaskSQLBuilder")
    void addTask(String title, String description, Long userID);
}
