package net.dengzixu.maine.mapper;

import net.dengzixu.maine.entity.dataobject.TaskSettingDO;
import net.dengzixu.maine.mapper.provider.TaskSettingMapperProvider;
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
    @ResultType(byte[].class)
    TaskSettingDO getSetting(Long taskID);
}
