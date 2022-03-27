package net.dengzixu.maine.mapper.task;

import net.dengzixu.maine.entity.TaskRecord;
import net.dengzixu.maine.entity.dataobject.ParticipantDO;
import net.dengzixu.maine.entity.dataobject.TakeRecordDO;
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
    void addRecord(Long taskID, Long userID);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "getRecordByTaskIDAndUserIDSQLBuilder")
    TaskRecord getRecordByTaskIDAndUserID(Long taskID, Long userID);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "getParticipantListByTaskIDSQLBuilder")
    List<ParticipantDO> getParticipantListByTaskID(Long taskID);

    @SelectProvider(type = TaskRecordMapperProvider.class, method = "listTakeRecordSQLBuilder")
    List<TakeRecordDO> listTakeRecord(Long userID);
}
