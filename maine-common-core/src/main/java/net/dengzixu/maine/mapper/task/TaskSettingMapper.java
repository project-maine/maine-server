package net.dengzixu.maine.mapper.task;

import net.dengzixu.maine.entity.TaskSetting;
import net.dengzixu.maine.mapper.provider.task.TaskSettingMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TaskSettingMapper {
    @InsertProvider(type = TaskSettingMapperProvider.class, method = "addSettingSQLBuilder")
    void addSetting(Long taskID, byte[] setting);

    @SelectProvider(type = TaskSettingMapperProvider.class, method = "getSettingSQLBuilder")
    TaskSetting getSetting(Long taskID);
}
