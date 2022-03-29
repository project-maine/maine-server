package net.dengzixu.maine.mapper.task;

import net.dengzixu.maine.entity.TaskRecord;
import net.dengzixu.maine.entity.dto.ParticipantDTO;
import net.dengzixu.maine.entity.dto.TakeRecordDTO;
import net.dengzixu.maine.mapper.provider.task.TaskRecordMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskRecordMapper {
    @InsertProvider(type = TaskRecordMapperProvider.class, method = "addRecordSQLBuilder")
    void addRecord(String serialID, Long taskID, Long userID,Integer status);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "getRecordByTaskIDAndUserIDSQLBuilder")
    TaskRecord getRecordByTaskIDAndUserID(Long taskID, Long userID);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "getParticipantListByTaskIDSQLBuilder")
    List<ParticipantDTO> getParticipantListByTaskID(Long taskID);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "listTakeRecordSQLBuilder")
    List<TakeRecordDTO> listTakeRecord(Long userID);
}
