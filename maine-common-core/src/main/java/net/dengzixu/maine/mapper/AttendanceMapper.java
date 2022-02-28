package net.dengzixu.maine.mapper;

import net.dengzixu.maine.mapper.provider.AttendanceMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttendanceMapper {
    @InsertProvider(type = AttendanceMapperProvider.class, method = "addTaskSQLBuilder")
    void addTask(String title, String description, Long userID);

    @InsertProvider(type = AttendanceMapperProvider.class, method = "addRecordSQLBuilder")
    void addRecord(Long taskID, Long userID);
}
