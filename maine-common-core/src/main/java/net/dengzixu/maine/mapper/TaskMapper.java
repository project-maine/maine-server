package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.Task;
import net.dengzixu.maine.mapper.provider.TaskMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    @InsertProvider(type = TaskMapperProvider.class, method = "addTaskSQLBuilder")
    void addTask(String title, String description, Long userID);

    @SelectProvider(type = TaskMapperProvider.class, method = "getTaskSQLBuilder")
    Task getTask(Long taskID);

    @SelectProvider(type = TaskMapperProvider.class, method = "getTaskListByUserID")
    List<Task> getTaskListByUserID(Long userID);
}
